package dao;

import java.util.List;
import java.util.Map;

import po.Variable;

/**
 * 
 * 变量DAO层接口
 *
 */

public interface VariableDao extends CountDao
{
	/**
	 * 变量新增
	 */
    void add(Variable variable);  
    /**
	 * 根据参数表获取变量信息
	 */
    List<Variable> getVariables(Map<String, Object> map);  
    /**
	 * 获取所有变量信息
	 */
    List<Variable> getAllVariables();  
    /**
	 * 修改变量
	 */
    void update(Variable variable);  
    /**
	 * 通过修改控件修改变量
	 */
    void updateByWidget(Variable variable);  
    /**
	 * 根据id删除变量
	 */
    void delete(Long id);  
    /**
	 * 根据控件id删除控件
	 */
    void deleteByWidget(Long id);  
    /**
	 * 根据项目id删除项目
	 */
    void deleteByProject(Long id);  
}
