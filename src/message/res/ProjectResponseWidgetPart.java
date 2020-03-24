package message.res;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import po.ValueItem;
import po.Variable;
import po.Widget;

/**
 * 用于项目相关接口，返回某项目下控件信息的response对象
 * 
 * 构造方法  ProjectResponseWidgetPart(Widget w)
 * @param w : Widget - 某项目下控件的基本信息
 * 
 * @author Bruce
 *
 */
public class ProjectResponseWidgetPart
{
    public ProjectResponseWidgetPart() {}

    /**
	 * @Description 根据传入的Widget控件基本信息，填入对应的应答格式内
	 * @param w : Widget - 某项目下控件的基本信息
	 */
    public ProjectResponseWidgetPart(Widget w)
    {
        setId(w.getId());
        setCode(w.getCode());
        setName(w.getName());
        setType(w.getType());
        setPositionX(w.getPositionX());
        List<Variable> variables = w.getVariables();
        if (variables == null || variables.size() == 0)
        {
            return;
        }
        Variable v = variables.get(0);
        if (v != null)
        {
            setValue(v.getValue());
            setUnit(v.getUnit());
        }
        List<ValueItem> items = v.getValueItems();
        if (items == null || items.size() == 0)
        {
            return;
        }
        map = new HashMap<String, Object>();
        for (ValueItem vi : items)
        {
            map.put(vi.getKey(), vi.getValue());
        }
        setMap(map);
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

    public Integer getPositionX() {
		return positionX;
	}

	public void setPositionX(Integer positionX) {
		this.positionX = positionX;
	}

	public Map<String, Object> getMap()
    {
        return map;
    }

    public void setMap(Map<String, Object> map)
    {
        this.map = map;
    }

    private Long id;
    private String code;
    private String name;
    private Integer type;
    private String value;
    private String unit;
    private Integer positionX;
    private Map<String, Object> map;
}
