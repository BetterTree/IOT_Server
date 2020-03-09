package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="资源不存在")
public class NotExistException extends ServiceException
{
    public static final int ERRCODE = 7;
    public NotExistException() {}
    
    /**
	 * 
	 * @param message : String - 错误信息
	 * @return 返回错误信息
	 */
    public NotExistException(String message)
    {
        super(message);
        errmsg = message;
    }
    
    private static final long serialVersionUID = 1L;
    {
        errcode = ERRCODE;
        errmsg = "资源不存在";
    }
}
