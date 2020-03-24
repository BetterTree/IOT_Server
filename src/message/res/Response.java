package message.res;

import org.springframework.dao.DataAccessException;

import abu.log.AbuLogger;
import exception.ServiceException;

/**
 * 可用于所有接口，应答格式统一返回resultCode结果码、resultMsg结果信息、data相关数据三项。
 * resultCode默认为0，resultMsg默认为success，若无data返回，则默认返回为data：null。
 * 
 * 构造方法  Response(Object data)
 * @param data : Object - 接口所需数据
 * 
 * 构造方法  Response(Exception e)
 * @param e : Exception - 异常信息
 * 
 * 构造方法  Response(int code, String msg)
 * @param code : int - 接口对应结果码
 * @param msg : String - 接口对应结果信息
 * 
 * @author Bruce
 *
 */
public class Response
{
    public Response() {}
    
    /**
	 * @Description 根据传入的Object对象，填入对应的应答格式内
	 * @param data : Object - 接口所需数据
	 */
    public Response(Object data)
    {
        this.data = data;
    }
    
    /**
	 * @Description 根据传入的Exception异常信息，进行判断并填入对应的应答格式内
	 * @param e : Exception - 异常信息
	 */
    public Response(Exception e)
    {
        AbuLogger.log.error(null, e);
        if (e instanceof DataAccessException)
        {
            resultcode = 100;
            resultmsg = e.getClass().toString();;
        }
        else if (e instanceof ServiceException)
        {
            ServiceException se = (ServiceException)e;
            resultcode = se.getErrcode();
            resultmsg = se.getErrmsg();
        }
        else
        {
            resultcode = -1;
            resultmsg = e.getMessage();
        }
    }
    
    /**
	 * @Description 根据传入的code结果码和msg结果信息，填入对应的应答格式内
	 * @param code : int - 接口对应结果码
	 * @param msg : String - 接口对应结果信息
	 */
    public Response(int code, String msg)
    {
        this.resultcode = code;
        this.resultmsg = msg;
    }
    
    public int getResultcode()
    {
        return resultcode;
    }
    
    public void setResultcode(int resultcode)
    {
        this.resultcode = resultcode;
    }
    
    public String getResultmsg()
    {
        return resultmsg;
    }

    public void setResultmsg(String resultmsg)
    {
        this.resultmsg = resultmsg;
    }
    
    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    private int resultcode = 0;
    private String resultmsg = "success";
    protected Object data;
    
    public static Response ok = new Response();
}
