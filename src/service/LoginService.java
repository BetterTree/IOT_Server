package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irandoo.base.crypto.password.RandooPassword;

import abu.log.AbuLogger;
import dao.UserDao;
import exception.ForbiddenException;
import exception.InternalServerError;
import exception.InvalidUserException;
import exception.ParamMissedException;
import exception.UnauthorizedException;
import message.req.ChgPwdReq;
import po.User;
import token.MyToken;

/**
 * 
 * 注册登录Service接口实现类
 * 包含了用户注册登录、根据id获取用户信息、重置密码
 */
@Service
public class LoginService
{
	/**
	 * 用户注册
	 * @param user : User - 用户信息
	 */
    @Transactional
    public void register(User user)
    {
        userService.add(user);
    }
    
    /**
     * 用户登录
	 * @param user : User - 用户信息
	 * @return
	 */
    public User login(User user)
    {
        return login(user, null, true);
    }
    
    /**
	 * @param user : User - 用户信息
	 * @param checkPwd : boolean - 判断重置密码
	 * @return
	 */
    public User login(User user, boolean checkPwd)
    {
        return login(user, null, checkPwd);
    }
    
    /**
	 * @param user : user - 用户信息
	 * @param expire : long - 用户登录的过期时间
	 * @param checkPwd : boolean - 判断重置密码
	 * @return
	 */
    public User login(User user, Long expire, boolean checkPwd)
    {
    	// 获取用户账号
        String userId = user.getUserId();
        if (userId == null)
        {
            System.out.println(
                String.format(
                    "参数userId[%s]\n",
                    userId
                )
            );
            throw new ParamMissedException("请求参数缺失");
        }
        
        User realUser = userDao.login(user);
        if (realUser == null)
        {
            AbuLogger.log.info(
                String.format(
                    "用户[%s]不存在",
                    userId
                )
            );
            throw new InvalidUserException("用户不存在");
        }
        
        if (checkPwd)
        {
            // 验证密码
            String userPwd = user.getUserPwd();
            boolean success = false;
            try
            {
                success = passor.checkin(userPwd, "UTF-8", realUser.getUserPwd());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new InternalServerError();
            }
            if (!success)
            {
                AbuLogger.log.info(
                    String.format(
                        "用户[%s]输入密码[%s]错误!",
                        userId, userPwd
                    )
                );
                throw new UnauthorizedException("输入密码错误!");
            }
        }
        
        // 验证通过以后把密码清除，避免前端拿到密码的密文信息
        realUser.setUserPwd("");
        realUser.setAnswer("");
        
        // 生成token
        String token = MyToken.genToken(realUser);
        realUser.setToken(token);
        
        return realUser;
    }
    
    /**
     * 根据标识获取用户信息
     * @param userId : String (IN) - 查询条件
     * @return : User - 返回符合条件的用户信息
     */
    public User getUser(String userId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        List<User> userList = userDao.getUsers(map);
        if (userList == null || userList.size() == 0)
        {
            return null;
        }
        return userList.get(0);
    }
    
    /**
     * 重置密码
	 * @param chgPwdReq : ChgPwdReq - 重置密码
	 * @return
	 */
    @Transactional
    public void chgPwd(ChgPwdReq chgPwdReq)
    {
        Long userId = chgPwdReq.getId();
        // 获取问题
        String question = chgPwdReq.getQuestion();
        if (question == null)
        {
            AbuLogger.log.error("缺失question参数");
            throw new ParamMissedException("缺失question参数");
        }
        // 获取回答
        String answer = chgPwdReq.getAnswer();
        if (answer == null)
        {
            AbuLogger.log.error("缺失answer参数");
            throw new ParamMissedException("缺失answer参数");
        }
        // 根据用户id获取用户信息
        User u = new User();
        u.setId(userId);
        u = userDao.login(u);
        if (u == null)
        {
            AbuLogger.log.error("用户已失效，无法登录");
            throw new InvalidUserException("用户已失效，无法登录");
        }
        // 先检查问题与回答是否一致
        if (!question.equals(u.getQuestion()) || !answer.equals(u.getAnswer()))
        {
            AbuLogger.log.error("问题回答错误，不能变更密码");
            throw new ForbiddenException("问题回答错误，不能变更密码");
        }
        // 获取新密码
        String newPwd = chgPwdReq.getNewPwd();
        if (newPwd == null)
        {
            // 如果传入为空，则设置密码为默认值
            newPwd = "123456";
        }
        // 改密
        try
        {
            newPwd = LoginService.passor.password(newPwd, "UTF-8");
        }
        catch (Exception e)
        {
            AbuLogger.log.error("密码加密错误");
            throw new InternalServerError();
        }
        u = new User();
        u.setUserPwd(newPwd);
        u.setId(userId);
        userDao.chgPwd(u);
    }
    
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    
    public static final RandooPassword passor = new RandooPassword();
    
    public static void main(String[] args) throws Exception
    {
        String plain = "123456";
        String pass = passor.password(plain, "UTF-8");
        System.out.println("pass=[" + pass + "]");
    }
}
