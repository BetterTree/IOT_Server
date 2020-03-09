package message.res;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import po.Device;

/**
 * 用于设备相关接口，返回某项目下的单个设备状态信息的response对象
 * 
 * 构造方法  DeviceStatusPart(Device d)
 * @param d : Device - 某项目下的单个设备信息
 * 
 * @author Bruce
 *
 */
public class DeviceStatusPart
{
    public DeviceStatusPart() {}

    /**
	 * @Description 根据传入的Device设备信息，填入对应的应答格式内
	 * @param d : Device - 某项目下的单个设备信息
	 */
    public DeviceStatusPart(Device d)
    {
        setId(d.getId());
        setCode(d.getCode());
        setName(d.getName());
        setStatus(d.realStatus);
//        synchronized (d)
//        {
        // 暂时不加锁，从业务功能看，风险极小
        setStatusName(d.getStatusName());
//        }
        setOnlineTime(new Timestamp(d.timestamp));
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = Device.statusToStr(status);
    }

    public String getStatusName()
    {
        return statusName;
    }

    public void setStatusName(String statusName)
    {
        this.statusName = statusName;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getOnlineTime()
    {
        return onlineTime;
    }

    public void setOnlineTime(Timestamp onlineTime)
    {
        this.onlineTime = onlineTime;
    }
    
    private Long id;
    private String code;
    private String name;
    private String status;
    private String statusName;
    private Timestamp onlineTime;
}
