package message.res;

import po.User;

/**
 * 用于验证用户帐号是否存在的接口，返回控件值增项的response对象
 * 
 * 构造方法  CheckUserResponse(User u)
 * @param u : User - 用户帐号的基本信息
 * 
 * @author Bruce
 *
 */
public class CheckUserResponse extends Response
{
    public CheckUserResponse() {}

    /**
	 * @Description 根据传入的User用户帐号信息判断，若User对象为空或id主键为空，则帐号不存在；反之，则帐号存在。
	 * @param u : User - 用户帐号信息
	 */
    public CheckUserResponse(User u)
    {
        if (u == null || u.getId() == null)
        {
            isExisted = false;
        }
        else
        {
            isExisted = true;
        }
    }
    
    public boolean isExisted()
    {
        return isExisted;
    }
    
    public void setExisted(boolean isExisted)
    {
        this.isExisted = isExisted;
    }
    
    private boolean isExisted;
}
