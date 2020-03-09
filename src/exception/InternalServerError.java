package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR, reason="内部处理错误")
public class InternalServerError extends ServiceException
{
    public static final int ERRCODE = 1;
    
    public InternalServerError() {}
    
    /**
	 * 
	 * @param message : String - 错误信息
	 * @return 返回错误信息
	 */
    public InternalServerError(String message)
    {
        super(message);
        errmsg = message;
    }
    
    private static final long serialVersionUID = 1L;
    {
        errcode = ERRCODE;
        errmsg = "内部处理错误";
    }
}
