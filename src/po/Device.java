package po;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 设备持久类
 *
 */

public class Device
{
    public static final int STATUS_UNKNOWN = 3;
    public static final int STATUS_ONLINE = 1;
    public static final int STATUS_UNSTABLE = 2;
    public static final int STATUS_OFFLINE = 0;
    public static final String[] STATUS_STR = {
        "offline", "online", "unstable", "unknown"
    };
    
    public static String statusToStr(int status)
    {
        return STATUS_STR[status];
    }
    
    public static boolean online(int status)
    {
        return status == STATUS_ONLINE;
    }
    
    public boolean unknown()
    {
        return realStatus == STATUS_UNKNOWN;
    }
    
    public boolean online()
    {
        return realStatus == STATUS_ONLINE;
    }
    
    public boolean offline()
    {
        return realStatus == STATUS_OFFLINE;
    }
    
    public boolean unstable()
    {
        return realStatus == STATUS_UNSTABLE;
    }
    
    public void turnUnknown()
    {
        realStatus = STATUS_UNKNOWN;
    }
    
    public void turnOnline()
    {
        realStatus = STATUS_ONLINE;
    }
    
    public void turnUnstable()
    {
        realStatus = STATUS_UNSTABLE;
    }
    
    public void turnOffline()
    {
        realStatus = STATUS_OFFLINE;
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

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
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

    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }
        Device p = (Device)o;
        return id == p.id;
    }
    
    @Override
    public int hashCode()
    {
        return id.intValue();
    }

    private Long id;  //设备主键
    private String code;  //识别码
    private String name;  //设备名
    private Integer type;  //类型
    private Integer status;  //状态
    private String statusName;  //状态说明，由下位机上报
    private Timestamp onlineTime;  //最后一次连接成功的时间节点
    private Project project;  //项目
    
    public volatile int realStatus = STATUS_UNKNOWN;  //真实状态
    public volatile long timestamp;  //时间戳
}
