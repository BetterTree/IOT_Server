package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.UNAUTHORIZED, reason="用户不存在")
public class InvalidUserException extends ServiceException
{
    public static final int ERRCODE = 5;
    
    public InvalidUserException() {}
    
    /**
	 * 
	 * @param message : String - 错误信息
	 * @return 返回错误信息
	 */
    public InvalidUserException(String message)
    {
        super(message);
        errmsg = message;
    }
    
    private static final long serialVersionUID = 1L;
    {
        errcode = ERRCODE;
        errmsg = "用户不存在";
    }
}
