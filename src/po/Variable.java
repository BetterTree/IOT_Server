package po;

import java.util.List;

/**
 * 
 * 变量持久类
 *
 */
public class Variable
{
    public static final int AIM_VIEW = 1;
    public static final int AIM_CONTROL = 2;
    
    public static final int TYPE_NUMBER     = 1;
    public static final int TYPE_STRING     = 2;
    public static final int TYPE_BOOLEAN    = 3;
    public static final int TYPE_DATETIME   = 4;
    public static final int TYPE_LIST       = 5;
    public static final int TYPE_DICT       = 6;
    
    /**
     * 判断是否为基本类型
     * @return
     */
    public boolean typePrimitive()
    {
        return typeNumber() || typeString() || typeBoolean() || typeDatetime();
    }
    
    /**
     * 判断是否为复合类型
     * @return
     */
    public boolean typeCompound()
    {
        return typeList() || typeDict();
    }
    
    /**
     * 判断类型是否为数字型
     * @return
     */
    public boolean typeNumber()
    {
        return type == TYPE_NUMBER;
    }
    
    /**
     * 判断类型是否为字符串型
     * @return
     */
    public boolean typeString()
    {
        return type == TYPE_STRING;
    }
    
    /**
     * 判断类型是否为布尔型
     * @return
     */
    public boolean typeBoolean()
    {
        return type == TYPE_BOOLEAN;
    }
    
    /**
     * 判断类型是否为日期型
     * @return
     */
    public boolean typeDatetime()
    {
        return type == TYPE_DATETIME;
    }
    
    /**
     * 判断类型是否为列表型
     * @return
     */
    public boolean typeList()
    {
        return type == TYPE_LIST;
    }
    
    /**
     * 判断类型是否为字典型
     * @return
     */
    public boolean typeDict()
    {
        return type == TYPE_DICT;
    }
    
    /**
     * 判断变量意图是否为显示型
     * @return
     */
    public boolean aimIsViewer()
    {
        return aim == AIM_VIEW;
    }
    
    /**
     * 判断变量意图是否为控制型
     * @return
     */
    public boolean aimIsController()
    {
        return aim == AIM_CONTROL;
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

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getAim()
    {
        return aim;
    }

    public void setAim(Integer aim)
    {
        this.aim = aim;
    }

    public Widget getWidget()
    {
        return widget;
    }

    public void setWidget(Widget widget)
    {
        this.widget = widget;
    }

    public List<ValueItem> getValueItems()
    {
        return valueItems;
    }

    public void setValueItems(List<ValueItem> valueItems)
    {
        this.valueItems = valueItems;
    }
    
    public void addValueItem(ValueItem vi)
    {
        valueItems.add(vi);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }
        Variable p = (Variable)o;
        return id == p.id;
    }
    
    @Override
    public int hashCode()
    {
        return id.intValue();
    }

    private Long id;  //变量主键
    private String code;  //识别码
    private String name;  //变量名
    private String value;  //当前值
    private String unit;  //值单位
    private Integer type;  //类型
    private Integer aim;  //意图
    private Widget widget;  //控件
    private List<ValueItem> valueItems;  //值增项
}
