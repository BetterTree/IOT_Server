package message.res;

import java.util.ArrayList;
import java.util.List;

import po.Device;

/**
 * 用于设备相关接口，返回某项目下的所有设备状态信息的response对象
 * 
 * 构造方法  DeviceStatusResponse(List<Device> devList)
 * @param devList : List<Device> - 某项目下的所有设备信息
 * 
 * @author Bruce
 *
 */
public class DeviceStatusResponse extends Response
{
    public DeviceStatusResponse() {}

    /**
	 * @Description 根据传入的List<Device>设备列表，填入对应的应答格式内
	 * @param devList : List<Device> - 某项目下的所有设备信息
	 */
    public DeviceStatusResponse(List<Device> devList)
    {
        if (devList == null)
        {
            return;
        }
        List<DeviceStatusPart> dspList = new ArrayList<DeviceStatusPart>();
        for (Device d : devList)
        {
            dspList.add(new DeviceStatusPart(d));
        }
        data = dspList;
    }
}
