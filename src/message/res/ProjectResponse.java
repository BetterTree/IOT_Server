package message.res;

import java.util.ArrayList;
import java.util.List;

import po.Project;
import po.User;
import po.Widget;

/**
 * 用于项目相关接口，返回项目基本信息的response对象
 * 
 * 构造方法 ProjectResponse(Project proj)
 * @param proj : Project - 项目的基本信息
 * 
 * @author Bruce
 *
 */
public class ProjectResponse
{
    public ProjectResponse() {}
    
    /**
	 * @Description 根据传入的Project项目基本信息，填入对应的应答格式内
	 * @param proj : Project - 项目的基本信息
	 */
    public ProjectResponse(Project proj)
    {
        setId(proj.getId());
        setCode(proj.getCode());
        setName(proj.getName());
        User u = proj.getUser();
        if (u != null)
        {
            setUserId(u.getUserId());
            setUserName(u.getUserName());
        }
        setRemark(proj.getRemark());
        setNote(proj.getNote());
        List<Widget> widgets = proj.getWidgets();
        if (widgets == null)
        {
            return;
        }
        List<ProjectResponseWidgetPart> widgetResList = new ArrayList<ProjectResponseWidgetPart>();
        for (Widget w : widgets)
        {
            widgetResList.add(new ProjectResponseWidgetPart(w));
        }
        setWidgets(widgetResList);
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

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
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

    public List<ProjectResponseWidgetPart> getWidgets()
    {
        return widgets;
    }

    public void setWidgets(List<ProjectResponseWidgetPart> widgets)
    {
        this.widgets = widgets;
    }

    private Long id;
    private String code;
    private String name;
    private String userId;
    private String userName;
    private String remark;
    private String note;
    private List<ProjectResponseWidgetPart> widgets;
}
