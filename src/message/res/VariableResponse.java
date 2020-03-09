package message.res;

import java.util.ArrayList;
import java.util.List;

import po.ValueItem;
import po.Variable;

/**
 * 用于控件相关接口，返回控件变量的response对象
 * 
 * 构造方法  VariableResponse(Variable v)
 * @param v : Variable - 控件变量的基本信息
 * 
 * @author Bruce
 *
 */
public class VariableResponse
{
    public VariableResponse() {}

    /**
	 * @Description 根据传入的Variable控件变量信息，填入对应的应答格式内
	 * @param v : Variable - 控件变量信息
	 */
    public VariableResponse(Variable v)
    {
        setId(v.getId());
        setCode(v.getCode());
        setName(v.getName());
        setValue(v.getValue());
        setUnit(v.getUnit());
        setType(v.getType());
        setAim(v.getAim());
        List<ValueItem> valueItems = v.getValueItems();
        if (valueItems != null)
        {
            List<ValueItemResponse> viResList = new ArrayList<ValueItemResponse>();
            for (ValueItem vi : valueItems)
            {
                viResList.add(new ValueItemResponse(vi));
            }
            setValueItems(viResList);
        }
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

    public List<ValueItemResponse> getValueItems()
    {
        return valueItems;
    }

    public void setValueItems(List<ValueItemResponse> valueItems)
    {
        this.valueItems = valueItems;
    }

    private Long id;
    private String code;
    private String name;
    private String value;
    private String unit;
    private Integer type;
    private Integer aim;
    private List<ValueItemResponse> valueItems;
}
