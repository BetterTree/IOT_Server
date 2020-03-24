package dao;

import java.util.List;
import java.util.Map;

import po.User;

/**
 * 
 * 用户DAO层接口
 *
 */
public interface UserDao extends CountDao
{
	/**
	 * 用户登录
	 */
    User login(User user);  
    /**
	 * 用户注册
	 */
    void add(User user);  
    /**
	 * 根据参数表获取用户信息
	 */
    List<User> getUsers(Map<String, Object> map);  
    /**
	 * 获取所有用户信息
	 */
    List<User> getAllUsers();  
    /**
	 * 修改用户信息
	 */
    void update(User user);  
    /**
	 * 修改密码
	 */
    void chgPwd(User user);  
    /**
	 * 根据id删除用户
	 */
    void delete(Long id);  
}
