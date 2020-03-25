package engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abu.callback.CallBack;
import abu.log.AbuLogger;
import abu.mqtt.Mqtt;
import abu.queue.scanner.LinkedScannerQueue;
import abu.systemutil.SystemUtil;
import conf.GlobalBean;
import exception.ForbiddenException;
import exception.InternalServerError;
import exception.NotExistException;
import exception.ParamMissedException;
import po.Device;
import po.Project;
import po.ValueItem;
import po.Variable;
import po.Widget;

/**
 * 对主控和传感器进行模拟操作的具体实现
 * 
 * @author Bruce
 *
 */
public class Instance
{
    public final static String MQTT_URL = GlobalBean.global.getMqttUrl();
    public final static String MQTT_USER = GlobalBean.global.getMqttUser();
    public final static String MQTT_PWD = GlobalBean.global.getMqttPwd();
    public final static String SOURCE_ARDUINO = "arduino";
    public final static String SOURCE_WEB = "web"; 
    
    public Instance(Project proj) throws Exception
    {
        project = proj;
        
        // 装载所有的使能控件
        assembleActiveWidgets(project.getWidgets());
        
        // 装载所有的设备
        assembleDevices();

        // 连接mqtt并订阅消息
        startMqttClient();
    }
    
    // 增加控件到实例中
    public void addWidget(Widget widget)
    {
        project = widget.getProject();
        
        // 装载指定的控件
        assembleActiveWidget(widget);
    }
    
    /**
     * 向下位机下达控制指令
     * @param widgetCode : String - 控件识别码
     * @param key : String - 控制部位名称
     * @throws Exception
     */
    public void command(String widgetCode, String key) throws Exception
    {
        Widget widget = null;
        // 根据控件识别码查找活动的控件
        synchronized (activeWidgets)
        {
            widget = activeWidgets.get(widgetCode);
        }
        if (widget == null)
        {
            throw new NotExistException("指定控件不存在或没有运行");
        }
        // 如果控件不是控制类，则报错，无法下达指令
        if (!widget.typeController())
        {
            throw new ForbiddenException("该控件不能下达指令");
        }
        Variable v = widget.getDefaultVariable();
        if (v == null)
        {
            throw new ForbiddenException("该控件没有设定默认变量");
        }
        if (!v.aimIsController())
        {
            throw new ForbiddenException("绑定的默认变量不是下达控制的类型");
        }
        String command = null;
        if (v.typePrimitive())
        {
            command = v.getValue();
            if (command == null)
            {
                command = key;
            }
        }
        else
        {
            if (key == null)
            {
                throw new ParamMissedException("请求必须指定控制部位的名称");
            }
            List<ValueItem> valueItems = v.getValueItems();
            if (valueItems != null)
            {
                for (ValueItem vi : valueItems)
                {
                    if (key.equals(vi.getKey()))
                    {
                        command = vi.getValue();
                        break;
                    }
                }
                if (command == null)
                {
                    // 如果没有匹配到，直接发键名
                    command = key;
                }
            }
            else
            {
                command = key;
            }
        }
        // 发布下达指令
        String topic = pubTopicHeader + widget.getCode();
        mqttClient.publish(topic, command, 2);
        AbuLogger.log.info("下达指令：topic=[" + topic + "], cmd=[" + command + "]");
    }
    
    // 在实例中解除指定控件，返回解除控件后剩余的控件数
    public int dismissWidget(Widget w)
    {
        String wcode = w.getCode();
        int widgetNum = 0;
        synchronized (activeWidgets)
        {
            activeWidgets.remove(wcode);
            widgetNum = activeWidgets.size();
        }
        return widgetNum;
    }
    
    /**
     * 断开mqtt连接
     */
    public void cleanup()
    {
        try
        {
            mqttClient.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        project = null;
        synchronized (activeWidgets)
        {
            activeWidgets.clear();
            activeWidgets = null;
        }
        synchronized (devices)
        {
            devices.clear();
            devices = null;
        }
        if (devScanner != null)
        {
            devScanner.clear();
            devScanner = null;
        }
    }
    
    // 装配使能控件
    private void assembleActiveWidgets(List<Widget> ws)
    {
        if (ws == null)
        {
            return;
        }
        for (Widget w : ws)
        {
            if (w.getEnable() == 0)
            {
                continue;
            }
            assembleActiveWidget(w);
        }
    }
    
    // 装配指定的控件
    private void assembleActiveWidget(Widget w)
    {
        String wcode = w.getCode();
        List<Variable> vs = w.getVariables();
        if (vs == null)
        {
            throw new InternalServerError("指定控件没有绑定变量！！");
        }
        if (!vs.isEmpty())
        {
            // 设置控件的默认变量为第一个，当变量编码缺失或者匹配不上，则使用该默认变量
            w.setDefaultVariable(vs.get(0));
            /*
            String vcode;
            Map<String, Variable> vm = null;
            for (Variable v : vs)
            {
                vcode = v.getCode();
                if (v.isViewer())
                {
                    vm = viewers.get(wcode);
                    if (vm == null)
                    {
                        vm = new HashMap<String, Variable>();
                        viewers.put(wcode, vm);
                    }
                }
                else if (v.isController())
                {
                    vm = controllers.get(wcode);
                    if (vm == null)
                    {
                        vm = new HashMap<String, Variable>();
                        controllers.put(wcode, vm);
                    }
                }
                else ;
                vm.put(vcode, v);
            }
            */
        }
        synchronized (activeWidgets)
        {
            activeWidgets.put(wcode, w);
        }
    }
    
    // 装配所有的设备
    private void assembleDevices()
    {
        // 创建设备状态扫描队列，每隔1秒扫描一次
        devScanner = new LinkedScannerQueue<Device>(
            3, // 延迟3秒启动
            1, // 时间间隔设定为1秒
            new CallBack() // 扫描队列时对每个元素的操作
            {
                @Override
                public Boolean call(Object... args)
                {
                    Device dev = (Device)args[0];
                    long now = System.currentTimeMillis();
                    // 判断已过去多久
                    // 如果已过去3秒以内，表示网络不稳定；
                    // 如果超过5秒，表示已失联
                    long delta = now - dev.timestamp;
                    if (delta > 5000)
                    {
                        dev.turnOffline();
                    }
                    else if (delta > 3000)
                    {
                        dev.turnUnstable();
                    }
                    else
                    {
                        dev.turnOnline();
                    }
                    return false;
                }
            }
        );
        devList = project.getDevices();
        if (devList == null)
        {
            return;
        }
        synchronized (devices)
        {
            for (Device d : devList)
            {
                devices.put(d.getCode(), d);
            }
        }
    }
    
    public List<Device> getDevList()
    {
        return devList;
    }
    
    // 连接mqtt并订阅消息
    private void startMqttClient() throws Exception
    {
        if (mqttClient.isOpened())
        {
            return;
        }
        // 保存发布的主题头，避免将来发布时多次拼装
        pubTopicHeader = String.format("%s/%s/", project.getCode(), SOURCE_WEB);
        // 连接MQTT Broker
        mqttClient.open();
        // 订阅上报消息
        String topic = String.format(
            "%s/%s/#",
            project.getCode(),
            SOURCE_ARDUINO
        );
        mqttClient.subscribe(topic, 2);
    }
    
    private Project project;
    private Map<String, Widget> activeWidgets = new HashMap<String, Widget>();
    private Map<String, Device> devices = new HashMap<String, Device>();
    private LinkedScannerQueue<Device> devScanner;
    private List<Device> devList;
    /*
    private Map<String, Map<String, Variable>> viewers = new HashMap<String, Map<String, Variable>>();
    private Map<String, Map<String, Variable>> controllers = new HashMap<String, Map<String, Variable>>();
    */
    private String pubTopicHeader; // 连接时保存发布的主题头，避免多次拼装
    private Mqtt mqttClient = new Mqtt(MQTT_URL, SystemUtil.getUID(), MQTT_USER, MQTT_PWD)
    {
        {
            // 定义订阅回调
            c = new CallBack()
            {
                @Override
                public Object call(Object... o)
                {
                    String topic = (String)o[0];
//                    int qos = (Integer)o[1];
                    byte[] payload = (byte[])o[2];
                    String payloadStr = SystemUtil.bin2String(payload);
                    // topic由三部分组成：项目识别码/arduino/控件编码
                    String[] segs = topic.split("/");
                    if (segs.length < 3)
                    {
                        AbuLogger.log.error("Wrong topic : " + topic);
                        return null;
                    }
                    String projCode = segs[0];
                    if (!projCode.equals(project.getCode()))
                    {
                        AbuLogger.log.error("Wrong project : " + projCode);
                        return null;
                    }
                    String source = segs[1];
                    if (!SOURCE_ARDUINO.equals(source))
                    {
                        AbuLogger.log.error("Wrong source : " + source);
                        return null;
                    }
                    String key = segs[2];
                    String value = payloadStr;
                    if ("status".equals(key))
                    {
                        // 主控状态上报
                        String deviceCode = value.substring(0, 16);
                        String status = value.substring(16);
                        status(deviceCode, status);
                    }
                    else if ("heartbeat".equals(key))
                    {
                        // 主控心跳
                        String deviceCode = value.substring(0, 16);
                        String status = value.substring(16);
                        heartbeat(deviceCode, status);
                    }
                    else
                    {
                        // 传感器数据上报
                        dataReport(key, value);
                    }
                    return null;
                }
            };
            // 定义丢链回调
            e = new CallBack()
            {
                @Override
                public Boolean call(Object... o)
                {
                    Throwable t = (Throwable)o[0];
                    t.printStackTrace();
//                    System.out.println("连接断开, 延时0.3s, 重新连接...");
                    try
                    {
                        Thread.sleep(300);
                    }
                    catch (Exception e) {}
                    // 返回true表示重新连接
                    return true;
                }
            };
            // 定义发布回调
            d = new CallBack()
            {
                @Override
                public Object call(Object... o)
                {
                    System.out.println("delivery completed!");
                    return null;
                }
            };
        }
        
        /**
         * 主控心跳处理
         * @param deviceCode : String - 主控识别码
         * @param statusStr : String - 状态码，固定为1
         */
        public void heartbeat(String deviceCode, String statusStr)
        {
            Device dev = null;
            synchronized (devices)
            {
                dev = devices.get(deviceCode);
            }
            if (dev == null)
            {
                return;
            }
            int status = Integer.parseInt(statusStr);
            if (!Device.online(status))
            {
                // 心跳报文不符合要求，忽略此次心跳报文
                AbuLogger.log.info("心跳报文不符合要求，忽略...");
                return;
            }
            // 如果设备不在队列中，将之插入队列
            if (dev.unknown())
            {
                devScanner.push(dev);
            }
//            synchronized (dev)
//            {
                dev.realStatus = status;
                // 暂时先删除，存库时从realStatus中读取
//                dev.setStatus(status);
                dev.timestamp = System.currentTimeMillis();
                // 暂时先删除，存库时从dev.timestamp中读取
//                dev.setOnlineTime(new Timestamp(dev.timestamp));
//            }
        }
        
        /**
         * 主控状态上报处理
         * @param deviceCode : String - 主控识别码
         * @param statusStr : String - 主控状态描述
         */
        public void status(String deviceCode, String statusStr)
        {
            Device dev = null;
            synchronized (devices)
            {
                dev = devices.get(deviceCode);
            }
            if (dev == null)
            {
                return;
            }
            synchronized (dev)
            {
                dev.setStatusName(statusStr);
            }
        }
        
        /**
         * 传感器数据上报
         * @param widgCode : String - 控件识别码
         * @param value : String - 上报的数据内容
         */
        public void dataReport(String widgCode, String value)
        {
            Widget widget = null;
            synchronized (activeWidgets)
            {
                widget = activeWidgets.get(widgCode);
            }
            if (widget == null)
            {
                AbuLogger.log.error("The widget doesn't exist : " + widgCode);
                return;
            }
            // 只有viewer控件才能赋值，其他类型忽略
            if (widget.typeViewer())
            {
                // 将值设置到variable中
                Variable var = widget.getDefaultVariable();
                if (var != null)
                {
                    AbuLogger.log.info("控件[" + widgCode + "]收到上报的数据，值为[" + value + "]");
                    var.setValue(value);
                }
            }
            else
            {
                AbuLogger.log.error("The type of widget isn't viewer");
                return;
            }
        }
    };
}

