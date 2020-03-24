package token;

import com.irandoo.base.jwt.JsonWebToken;

import po.User;

/**
 * 与鉴权token相关，目前只提供生成token的方法
 * 
 * token生成方法  genToken(User user)
 * @param user : User - 当前登录的user信息
 * @return String - 返回token令牌字符串
 * 
 * @author Bruce
 *
 */
public class MyToken
{
	/**
	 * @Description 根据当前登录的user信息生成token令牌，用来鉴权
	 * @param user : User - 当前登录的user信息
	 * @return String - 返回token令牌字符串
	 */
    public static String genToken(User user)
    {
        if (user == null)
        {
            return "";
        }
        // 生成token
        MyPayload payload = null;
        payload = new MyPayload();
        payload.setUserId(user.getId());
        JsonWebToken token = new JsonWebToken(payload);
        return token.toString();
    }
}
