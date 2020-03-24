package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_IMPLEMENTED, reason="服务器不支持所请求的功能")
public class NotImplementedException extends ServiceException
{
    public static final int ERRCODE = 6;
    
    public NotImplementedException() {}
    
    /**
	 * 
	 * @param message : String - 错误信息
	 * @return 返回错误信息
	 */
    public NotImplementedException(String message)
    {
        super(message);
        errmsg = message;
    }
    
    private static final long serialVersionUID = 1L;
    {
        errcode = ERRCODE;
        errmsg = "服务器不支持所请求的功能";
    }
}
