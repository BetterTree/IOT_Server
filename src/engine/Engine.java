package engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.ForbiddenException;
import po.Device;
import po.Project;
import po.Widget;

/**
 * 对主控和传感器进行模拟操作
 * 
 * @author Bruce
 *
 */
public class Engine
{
    /**
     * 启动项目下所有控件
     * @param proj : Project - 项目信息
     * @throws Exception
     */
    public static void regInstance(Project proj) throws Exception
    {
        if (proj == null)
        {
            return;
        }
        String code = proj.getCode();
        Instance instance = null;
        synchronized (map)
        {
            instance = map.get(code);
            if (instance != null)
            {
                instance.cleanup();
            }
            map.put(code, new Instance(proj));
        }
    }
    
    /**
     * 启动单个控件
     * @param widget : Widget - 控件信息
     * @throws Exception
     */
    public static void regInstance(Widget widget) throws Exception
    {
        Project proj = widget.getProject();
        String code = proj.getCode();
        Instance instance = null;
        synchronized (map)
        {
            instance = map.get(code); 
            if (instance == null)
            {
                map.put(code, instance = new Instance(proj));
            }
        }
        instance.addWidget(widget);
    }
    
    /**
     * 向下位机下达控制指令
     * @param projCode : String - 项目识别码
     * @param widgetCode : String - 控件识别码
     * @param key : String - 控制部位名称
     * @throws Exception
     */
    public static void command(String projCode, String widgetCode, String key) throws Exception
    {
        Instance instance = getInstance(projCode);
        if (instance == null)
        {
            throw new ForbiddenException("项目/控件不是运行态");
        }
        instance.command(widgetCode, key);
    }
    
    /**
     * 根据项目识别码获取某项目下的所有设备信息，包含状态信息
     * @param projCode : String - 项目识别码
     */
    public static List<Device> getDevices(String projCode)
    {
        Instance instance = getInstance(projCode);
        if (instance == null)
        {
            throw new ForbiddenException("项目/控件不是运行态");
        }
        return instance.getDevList();
    }
    
    /**
     * 停止运行项目
     * @param proj : Project - 项目信息
     */
    public static void delInstance(Project proj)
    {
        if (proj == null)
        {
            return;
        }
        String code = proj.getCode();
        Instance instance = null;
        synchronized (map)
        {
            instance = map.remove(code);
        }
        if (instance != null)
        {
            instance.cleanup();
        }
    }
    
    /**
     * 停止运行单个控件
     * @param widget : Widget - 控件信息
     */
    public static void delInstance(Widget widget)
    {
        if (widget == null)
        {
            return;
        }
        Project proj = widget.getProject();
        String code = proj.getCode();
        Instance instance = null;
        synchronized (map)
        {
            instance = map.get(code);
        }
        if (instance == null)
        {
            return;
        }
        int leftWidgetNum = instance.dismissWidget(widget);
        if (leftWidgetNum == 0)
        {
            synchronized (map)
            {
                instance = map.remove(code);
            }
            if (instance != null)
            {
                instance.cleanup();
            }
        }
    }
    
    /**
     * 根据识别码获取信息
     * @param code : String - 识别码
     */
    public static Instance getInstance(String code)
    {
        Instance instance = null;
        synchronized (map)
        {
            instance = map.get(code);
        }
        return instance;
    }

    private static final Map<String, Instance> map = new HashMap<String, Instance>();
}
