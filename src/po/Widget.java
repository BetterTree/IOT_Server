package po;

import java.util.ArrayList;
import java.util.List;

import exception.ParamMissedException;
import message.req.WidgetReq;

/**
 * 
 * 控件持久类
 *
 */
public class Widget
{
    public static final int ENABLE = 1;
    public static final int DISNABLE = 0;
    public static final int VISIBLE = 1;
    public static final int INVISIBLE = 0;
    
    // 类型掩码
    public static final int TYPE_MASK       = 0xFF000000;
    
    // 显示类控件（0x01000001 ~ 0x7FFFFFFF）
    // 数值类（0x01000001 ~ 0x01FFFFFF）
    public static final int TYPE_NUMERIC    = 0x1000000;
    // 布尔类（0x02000001 ~ 0x02FFFFFF）
    public static final int TYPE_BOOLEAN    = 0x2000000;
    // 时间类（0x03000001 ~ 0x03FFFFFF）
    public static final int TYPE_DATETIME   = 0x3000000;
    
    // 控制类控件（0x80000001 ~ 0xFEFFFFFF）
    // 开关类（0x80000001 ~ 0x80FFFFFF）
    public static final int TYPE_SWITCH     = 0x80000000;
    // 多功能组件类（0x81000001 ~ 0x81FFFFFF）
    public static final int TYPE_MULTIFUN   = 0x81000000;
    // 方向类（0x82000001 ~ 0x82FFFFFF）
    public static final int TYPE_DIRECTION  = 0x82000000;
    // 滚动条类（0x83000001 ~ 0x83FFFFFF）
    public static final int TYPE_SCROLLBAR  = 0x83000000;
    
    public Widget() {}
    
    public Widget(Long projId, WidgetReq req)
    {
        code = req.getCode();
        if (code == null || code.isEmpty())
        {
            throw new ParamMissedException("缺失控件识别码参数");
        }
        type = req.getType();
        if (type == null)
        {
            throw new ParamMissedException("缺失控件type参数");
        }
        name = req.getName();
        positionX = req.getPositionX();
        variables = new ArrayList<Variable>();
        project = new Project();
        project.setId(projId);
    }
    
    public static boolean typeViewer(Integer type)
    {
        return type > 0;
    }
    
    // 判断控件是否为显示大类
    public boolean typeViewer()
    {
        return type > 0;
    }
    
    public static boolean typeController(Integer type)
    {
        return type < 0;
    }
    
    // 判断控件是否为控制大类
    public boolean typeController()
    {
        return type < 0;
    }
    
    public static int typeCategory(Integer type)
    {
        return type & TYPE_MASK;
    }
    
    // 获取类型的类别值
    public int typeCategory()
    {
        return type & TYPE_MASK;
    }
    
    public static boolean typeNumeric(Integer type)
    {
        return (type & TYPE_MASK) == TYPE_NUMERIC;
    }
    
    // 判断控件是否为数值类
    public boolean typeNumeric()
    {
        return (type & TYPE_MASK) == TYPE_NUMERIC;
    }
    
    public static boolean typeBoolean(Integer type)
    {
        return (type & TYPE_MASK) == TYPE_BOOLEAN;
    }
    
    // 判断控件是否为布尔类
    public boolean typeBoolean()
    {
        return (type & TYPE_MASK) == TYPE_BOOLEAN;
    }
    
    public static boolean typeDateTime(Integer type)
    {
        return (type & TYPE_MASK) == TYPE_DATETIME;
    }
    
    // 判断控件是否为时间类
    public boolean typeDateTime()
    {
        return (type & TYPE_MASK) == TYPE_DATETIME;
    }
    
    public static boolean typeSwitch(Integer type)
    {
        return (type & TYPE_MASK) == TYPE_SWITCH;
    }
    
    // 判断控件是否为开关类
    public boolean typeSwitch()
    {
        return (type & TYPE_MASK) == TYPE_SWITCH;
    }
    
    public static boolean typeMultiFun(Integer type)
    {
        return (type & TYPE_MASK) == TYPE_MULTIFUN;
    }
    
    // 判断控件是否为多功能组件类
    public boolean typeMultiFun()
    {
        return (type & TYPE_MASK) == TYPE_MULTIFUN;
    }
    
    public static boolean typeDirection(Integer type)
    {
        return (type & TYPE_MASK) == TYPE_DIRECTION;
    }
    
    // 判断控件是否为方向类
    public boolean typeDirection()
    {
        return (type & TYPE_MASK) == TYPE_DIRECTION;
    }
    
    public static boolean typeScrollBar(Integer type)
    {
        return (type & TYPE_MASK) == TYPE_SCROLLBAR;
    }
    
    // 判断控件是否为滚动条类
    public boolean typeScrollBar()
    {
        return (type & TYPE_MASK) == TYPE_SCROLLBAR;
    }
    
    public boolean enabled()
    {
        return enable == ENABLE;
    }
    
    public boolean visible()
    {
        return visible == VISIBLE;
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

    public Integer getEnable()
    {
        return enable;
    }

    public void setEnable(Integer enable)
    {
        this.enable = enable;
    }

    public Integer getVisible()
    {
        return visible;
    }

    public void setVisible(Integer visible)
    {
        this.visible = visible;
    }

    public Integer getPositionX()
    {
        return positionX;
    }

    public void setPositionX(Integer positionX)
    {
        this.positionX = positionX;
    }

    public Integer getPositionY()
    {
        return positionY;
    }

    public void setPositionY(Integer positionY)
    {
        this.positionY = positionY;
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setWidth(Integer width)
    {
        this.width = width;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setHeight(Integer height)
    {
        this.height = height;
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }

    public List<Variable> getVariables()
    {
        return variables;
    }

    public void setVariables(List<Variable> variables)
    {
        this.variables = variables;
    }

    public Variable getDefaultVariable()
    {
        return defaultVariable;
    }

    public void setDefaultVariable(Variable defaultVariable)
    {
        this.defaultVariable = defaultVariable;
    }

    public void addVariable(Variable v)
    {
        variables.add(v);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }
        Widget p = (Widget)o;
        return id == p.id;
    }
    
    @Override
    public int hashCode()
    {
        return id.intValue();
    }

    private Long id;  //控件主键
    private String code;  //识别码
    private String name;  //控件名
    private Integer type;  //传感器模块类型
    private Integer enable = 1;  //有效性
    private Integer visible = 1;  //可见性
    private Integer positionX;  //起始X轴坐标 / 显示序号，从1开始
    private Integer positionY;  //起始Y轴坐标 / 每行显示个数
    private Integer width;  //宽度
    private Integer height;  //高度
    private Project project;  //项目
    private List<Variable> variables;  //变量
    // 在根据变量编码匹配不到变量时，启用默认的变量（实例运行时取第一个变量）
    private Variable defaultVariable;  //默认变量
}
