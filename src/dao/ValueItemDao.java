package dao;

import java.util.List;
import java.util.Map;

import po.ValueItem;

/**
 * 
 * 值增项DAO层接口
 *
 */

public interface ValueItemDao extends CountDao
{
	/**
	 * 值增项新增
	 */
    void add(ValueItem valueItem);  
    /**
	 * 根据参数表获取值增项信息
	 */
    List<ValueItem> getValueItems(Map<String, Object> map);  
    /**
	 * 获取所有值增项信息
	 */
    List<ValueItem> getAllValueItems();  
    /**
	 * 修改值增项
	 */
    void update(ValueItem valueItem);  
    /**
	 * 根据id删除值增项
	 */
    void delete(Long id);  
    /**
	 * 根据id删除变量
	 */
    void deleteByVariable(Long id);  
    /**
	 * 根据id删除项目
	 */
    void deleteByProject(Long id);  
    /**
	 * 根据id删除控件
	 */
    void deleteByWidget(Long id);  
}
