package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.irandoo.base.jwt.JsonWebToken;

import exception.UnauthorizedException;
import token.MyPayload;

/**
 * WEB前端的用户授权拦截器，前端发起请求时，往Authorization里塞入Bearer类型的token，后端截取验证，验证通过，则授权该用户登录。
 * 
 * 处理方法  preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
 * @param request ： HttpServletRequest - 客户端请求
 * @param response ： HttpServletResponse - 客户端应答
 * @param handler ： Object - 待定
 * 
 * @author Bruce
 *
 */
public class AuthInterceptor implements HandlerInterceptor
{
	/**
     * 用户授权拦截器，后端从客户端请求中的Authorization内截取Bearer类型的token进行验证，验证通过，则授权该用户登录。
	 * @param request ： HttpServletRequest - 客户端请求
	 * @param response ： HttpServletResponse - 客户端应答
	 * @param handler ： Object - 待定
     * @throws Exception
     */
    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler) throws Exception
    {
        if (request.getMethod().equals(RequestMethod.OPTIONS.name()))
        {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,Authorization,x-access-token");
            return true;
        }
        String auth = request.getHeader("authorization");
        if (auth == null)
        {
            throw new UnauthorizedException("用户没有登录，请先登录");
        }
        int index = auth.indexOf(' ');
        if (index < 0)
        {
            throw new UnauthorizedException("缺失令牌，请登录");
        }
        String type = auth.substring(0, index);
        if (!"Bearer".equals(type))
        {
            throw new UnauthorizedException("鉴权类型不是Bearer，请检查");
        }
        String token = auth.substring(index+1);
        JsonWebToken jwt = JsonWebToken.getInstance(token, MyPayload.class);
        if (jwt == null)
        {
            throw new UnauthorizedException("令牌不正确，鉴权失败");
        }
        // 得到令牌的载荷信息
        MyPayload loginPayload = (MyPayload)jwt.getPayload();
        if (loginPayload == null)
        {
            throw new UnauthorizedException("令牌负载错误，鉴权失败");
        }
        Long expire = loginPayload.getExpire();
        if ((expire == null)
            || (expire < System.currentTimeMillis()))
        {
            throw new UnauthorizedException("令牌已失效，鉴权失败");
        }
        Long userId = loginPayload.getUserId();
        
        request.setAttribute("userId", userId);
        return true;
    }
    
    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView) throws Exception
    {
    }
    
    @Override
    public void afterCompletion(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler, Exception e) throws Exception
    {
    }
}
