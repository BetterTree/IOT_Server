package test;

import abu.callback.CallBack;
import abu.mqtt.Mqtt;
import abu.systemutil.SystemUtil;

/**
 * 下位机模拟测试程序
 * @author Bruce
 *
 */
public class Device
{
    // 定义mqtt客户端对象
    private static Mqtt mqttClient = new Mqtt(
        "tcp://47.92.196.101:1883",
        SystemUtil.getUID(),
        "manager",
        "manager")
    {
        {
            // 定义发布回调
            d = new CallBack()
            {
                @Override
                public Object call(Object... o)
                {
                    return null;
                }
            };
        }
    };
    
    /**
     * 测试驱动程序
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        mqttClient.open();
        // 模拟下位机瞬间发出1000个数据，考察后端的处理是否正常
        for (int i = 1; i <= 1000; i++)
        {
            mqttClient.publish("0791581534711797/arduino/A1", "1375");
        }
        mqttClient.close();
    }
}
