package message.res;

import po.ValueItem;

/**
 * 用于控件相关接口，返回控件值增项的response对象
 * 
 * 构造方法  ValueItemResponse(Variable vi)
 * @param vi : ValueItem - 控件值增项的基本信息
 * 
 * @author Bruce
 *
 */
public class ValueItemResponse
{
    public ValueItemResponse() {}

    /**
	 * @Description 根据传入的ValueItem控件值增项信息，填入对应的应答格式内
	 * @param vi : ValueItem - 控件值增项信息
	 */
    public ValueItemResponse(ValueItem vi)
    {
        setId(vi.getId());
        setKey(vi.getKey());
        setValue(vi.getValue());
    }
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    private Long id;
    private String key;
    private String value;
}
