package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import message.req.ChgPwdReq;
import message.res.CheckUserResponse;
import message.res.Response;
import po.User;
import service.LoginService;


/**
 * 
 * 登录控制器类
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController
{
	 /**
     * 用户登录
	 * @param user : User - 用户信息
	 * @return
	 */
    @RequestMapping(method=RequestMethod.POST)
    public Response login(@RequestBody User user)
    {
        try
        {
            user = loginService.login(user);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(user.getToken());
    }
    
    /**
	 * 用户注册
	 * @param user : User - 用户信息
	 */
    @RequestMapping(value="register", method=RequestMethod.POST)
    public Response register(@RequestBody User user)
    {
        try
        {
            loginService.register(user);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 根据标识获取用户信息
     * @param userId : String (IN) - 查询条件
     * @return : User - 返回符合条件的用户信息
     */
    @RequestMapping(value="userId/{userId}", method=RequestMethod.GET)
    public Response getUserByUserId(@PathVariable("userId") String userId)
    {
        User u = null;
        try
        {
            u = loginService.getUser(userId);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(u);
    }
    
    /**
     * 根据标识判断用户是否存在
     * @param userId : String (IN) - 查询条件
     * @return
     */
    @RequestMapping(value="checkUser/{userId}", method=RequestMethod.GET)
    public Response checkUserByUserId(@PathVariable("userId") String userId)
    {
        Response res = null;
        try
        {
            res = new CheckUserResponse(loginService.getUser(userId));
        }
        catch (Exception e)
        {
            res = new Response(e);
        }
        return res;
    }
    
    /**
     * 重置密码
	 * @param chgPwdReq : ChgPwdReq - 重置密码
	 * @return
	 */
    @RequestMapping(value="chgPwd", method=RequestMethod.PUT)
    public Response chgPwd(@RequestBody ChgPwdReq chgPwdReq)
    {
        try
        {
            loginService.chgPwd(chgPwdReq);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    @Autowired
    private LoginService loginService;
}
