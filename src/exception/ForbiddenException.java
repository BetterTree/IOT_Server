package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.FORBIDDEN, reason="禁止操作，可能业务权限不够")
public class ForbiddenException extends ServiceException
{
    public static final int ERRCODE = 3;
    
    public ForbiddenException() {}
    
    /**
	 * 
	 * @param message : String - 错误信息
	 * @return 返回错误信息
	 */
    public ForbiddenException(String message)
    {
        super(message);
        errmsg = message;
    }
    
    private static final long serialVersionUID = 1L;
    {
        errcode = ERRCODE;
        errmsg = "禁止操作，可能业务权限不够";
    }
}
