package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import exception.InternalServerError;
import po.User;

/**
 * 
 * 用户Service接口实现类
 * 包含了用户新增、根据用户id获取用户信息、根据参数表获取用户信息、修改用户、重置密码、根据用户id删除用户、根据参数表获取分页信息
 */

@Service
public class UserService extends PagingService
{
	/**
	 * 用户新增
	 * @param user : User - 用户信息
	 * 
	 */
    @Transactional
    public void add(User user)
    {
        // 如果有密码，进行加密再存库
        String password = user.getUserPwd();
        if (password != null && !password.isEmpty())
        {
            try
            {
                password = LoginService.passor.password(password, "UTF-8");
            }
            catch (Exception e)
            {
                System.out.println("密码加密错误");
                throw new InternalServerError("密码加密错误");
            }
            user.setUserPwd(password);
        }
        userDao.add(user);
    }
    
    /**
     * 根据用户id获取用户信息
	 * @param id : Long - 用户id
	 * @return
	 */
    public User getUser(Long id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        List<User> userList = userDao.getUsers(map);
        if (userList == null || userList.size() == 0)
        {
            return null;
        }
        return userList.get(0);
    }
    
    /**
     * 根据参数表获取用户信息
	 * @param map - 参数表
	 * @return
	 */
    public Map<String, Object> getUsers(Map<String, Object> map)
    {
        List<User> users =
            map == null ?
            userDao.getAllUsers() : userDao.getUsers(refreshParam(map));
        return getResult(users, map);
    }
    
    /**
     * 修改用户
	 * @param user : User - 用户信息
	 */
    @Transactional
    public void update(User user)
    {
        userDao.update(user);
    }
    
    /**
     * 重置密码
	 * @param uid : Long - 用户id
	 */
    @Transactional
    public void resetUserPwd(Long uid)
    {
        // 设置为默认密码：123456的密文
        String defaultPwd = "6mogqAOdOHZ3Xb0UM1qMGA==";
        User u = new User();
        u.setUserPwd(defaultPwd);
        u.setId(uid);
        userDao.chgPwd(u);
    }
    
    /**
     * 根据用户id删除用户
	 * @param id : Long - 用户id
	 */
    @Transactional
    public void delete(Long id)
    {
        userDao.delete(id);
    }
    
    /**
     * 根据参数表获取分页信息
	 * @param map - 参数表
	 * @return
	 */
    @Override
    protected int getRows(Map<String, Object> map)
    {
        return userDao.getTableCount(map);
    }

    @Autowired
    private UserDao userDao;
}
