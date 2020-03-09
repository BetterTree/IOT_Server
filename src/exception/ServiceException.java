package exception;

/**
 * @author Bruce
 * @date 2019/07/26
 */
public class ServiceException extends RuntimeException
{
    public static final int ERRCODE = -1;
    
    public ServiceException()
    {
        errmsg = "unspecified";
    }
    
    public ServiceException(String message)
    {
        super(message);
        errmsg = message;
    }
    
    public int getErrcode()
    {
        return errcode;
    }
    
    public String getErrmsg()
    {
        return errmsg == null ? getMessage() : errmsg;
    }
    
    private static final long serialVersionUID = 1L;
    protected int errcode = ERRCODE;
    protected String errmsg;
}
