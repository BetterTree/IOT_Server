package po;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 项目持久化类
 *
 */

public class Project
{
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

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime)
    {
        this.updateTime = updateTime;
    }

    public List<Widget> getWidgets()
    {
        return widgets;
    }

    public void setWidgets(List<Widget> widgets)
    {
        this.widgets = widgets;
    }

    public List<Device> getDevices()
    {
        return devices;
    }

    public void setDevices(List<Device> devices)
    {
        this.devices = devices;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }
        Project p = (Project)o;
        return id == p.id;
    }
    
    @Override
    public int hashCode()
    {
        return id.intValue();
    }

    private Long id;  //项目主键
    private String code;  //识别码
    private String name;  //项目名
    private User user;  //用户
    private String remark;  //项目备注信息
    private String note;  //项目笔记信息
    private Timestamp updateTime;  //更新时间
    private List<Widget> widgets;  //控件
    private List<Device> devices;  //设备
}
