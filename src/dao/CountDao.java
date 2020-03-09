package dao;

import java.util.Map;

/**
 * 
 * 总记录数DAO接口
 *
 */
public interface CountDao
{
	/**
	 * 获取总记录数
	 */
    int getTableCount(/*@Param("map")*/ Map<String, Object> map);
}
