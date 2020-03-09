package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import abu.systemutil.SystemUtil;
import dao.VariableDao;
import po.Variable;

/**
 * 
 * 变量Service接口实现类
 * 包含了变量新增、根据变量id获取变量信息、根据参数表获取变量信息、修改变量、根据变量id删除变量、根据参数表获取分页信息
 */

@Service
public class VariableService extends PagingService
{
	/**
	 * 变量新增
	 * @param variable : Variable- 变量信息
	 * 
	 */
    @Transactional
    public void add(Variable variable)
    {
        // 如果不携带编码，则自动生成唯一码
        String code = variable.getCode();
        if (code == null || code.isEmpty())
        {
            variable.setCode(SystemUtil.getUID());
        }
        variableDao.add(variable);
    }
    
    /**
     * 根据变量id获取变量信息
	 * @param id : Long - 变量id
	 * @return
	 */
    public Variable getVariable(Long id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        List<Variable> variableList = variableDao.getVariables(map);
        if (variableList == null || variableList.size() == 0)
        {
            return null;
        }
        return variableList.get(0);
    }
    
    /**
     * 根据参数表获取变量信息
	 * @param map - 参数表
	 * @return
	 */
    public Map<String, Object> getVariables(Map<String, Object> map)
    {
        List<Variable> variables =
            map == null ?
            variableDao.getAllVariables() : variableDao.getVariables(refreshParam(map));
        return getResult(variables, map);
    }
    
    /**
     * 修改变量
	 * @param variable : Variable- 变量信息
	 */
    @Transactional
    public void update(Variable variable)
    {
        variableDao.update(variable);
    }
    
    /**
     * 根据变量id删除变量
	 * @param id : Long - 变量id
	 * 
	 */
    @Transactional
    public void delete(Long id)
    {
        variableDao.delete(id);
    }
    
    /**
     * 根据参数表获取分页信息
	 * @param map - 参数表
	 * @return
	 */
    @Override
    protected int getRows(Map<String, Object> map)
    {
        return variableDao.getTableCount(map);
    }

    @Autowired
    private VariableDao variableDao;
}
