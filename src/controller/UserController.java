package controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import message.res.Response;
import po.User;
import service.UserService;

/**
 * 
 * 用户控制器类
 *
 */

@RestController
@RequestMapping("/user")
public class UserController
{
	/**
	 * 用户新增
	 * @param user : User - 用户信息
	 * @return
	 */
    @RequestMapping(method=RequestMethod.POST)
    public Response add(@RequestBody User user)
    {
        try
        {
            userService.add(user);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 根据参数表获取用户信息
	 * @param map - 参数表
	 * @return
	 */
    @RequestMapping(method=RequestMethod.GET)
    public Response getUsers(@RequestParam Map<String, Object> map)
    {
        try
        {
            map = userService.getUsers(map);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(map);
    }
    
    /**
     * 获取用户信息
	 * @return
	 */
    @RequestMapping(value="all", method=RequestMethod.GET)
    public Response getUsers()
    {
        Map<String, Object> map = null;
        try
        {
            map = userService.getUsers(null);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(map);
    }
    
    /**
     * 获取用户信息
	 * @return
	 */
    @RequestMapping(value="id", method=RequestMethod.GET)
    public Response getUser(@RequestAttribute("userId") Long userId)
    {
        User u = null;
        try
        {
            u = userService.getUser(userId);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(u);
    }
    
    /**
     * 修改用户
	 * @param user : User - 用户信息
	 */
    @RequestMapping(method=RequestMethod.PUT)
    public Response update(@RequestBody User user)
    {
        try
        {
            userService.update(user);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 重置密码为123456，该接口必须由超级管理员操作，需要在业务模块检查操作权限
     * @param uid : Long (IN) - 待重置密码的用户
     */
    @RequestMapping(value="resetPwd/{uid}", method=RequestMethod.PUT)
    public Response resetUserPwd(@PathVariable("uid") Long uid)
    {
        try
        {
            userService.resetUserPwd(uid);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 根据用户id删除用户
	 * @param id : Long - 用户id
	 */
    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public Response delete(@PathVariable("id") Long id)
    {
        try
        {
            userService.delete(id);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    @Autowired
    private UserService userService;
}
