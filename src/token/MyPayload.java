package token;

import com.irandoo.base.jwt.Payload;

/**
 * 与鉴权token相关，存入token的有效载荷
 * 
 * 构造方法  MyPayload()
 * 默认初始化token口令的失效时间为24小时
 * 
 * @author Bruce
 *
 */
public class MyPayload extends Payload
{
    // 口令失效时间：24h
    public static final Long EXPIRE_TIME = 24L * 60L * 60L * 1000L;
    
    /**
     * 失效时间取自参数设定
     */
    public MyPayload()
    {
        long now = System.currentTimeMillis();
        set("expire", now + EXPIRE_TIME);
    }
    
    public void setUserId(Long userId)
    {
        set("userId", userId);
    }
    
    public Long getUserId()
    {
        return (Long)get("userId");
    }
    
    public void setExpire(Long expire)
    {
        set("expire", expire);
    }
    
    public Long getExpire()
    {
        return (Long)get("expire");
    }
}
