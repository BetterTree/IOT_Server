package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="缺失必须的参数")
public class ParamMissedException extends ServiceException
{
    public static final int ERRCODE = 2;
    
    public ParamMissedException() {}
    
    /**
	 * 
	 * @param message : String - 错误信息
	 * @return 返回错误信息
	 */
    public ParamMissedException(String message)
    {
        super(message);
        errmsg = message;
    }
    
    private static final long serialVersionUID = 1L;
    {
        errcode = ERRCODE;
        errmsg = "缺失必须的参数";
    }
}
