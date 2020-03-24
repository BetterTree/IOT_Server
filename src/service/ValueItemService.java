package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.ValueItemDao;
import po.ValueItem;

/**
 * 
 * 值增项Service接口实现类
 * 包含了值增项新增、根据值增项id获取值增项信息、根据参数表获取值增项信息、修改值增项、根据值增项id删除值增项、根据参数表获取分页信息
 */

@Service
public class ValueItemService extends PagingService
{
	/**
	 * 值增项新增
	 * @param valueItem : ValueItem- 值增项信息
	 * 
	 */
    @Transactional
    public void add(ValueItem valueItem)
    {
        valueItemDao.add(valueItem);
    }
    
    /**
     * 根据值增项id获取值增项信息
	 * @param id : Long - 值增项id
	 * @return
	 */
    public ValueItem getValueItem(Long id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        List<ValueItem> valueItemList = valueItemDao.getValueItems(map);
        if (valueItemList == null || valueItemList.size() == 0)
        {
            return null;
        }
        return valueItemList.get(0);
    }
    
    /**
     * 根据参数表获取值增项信息
	 * @param map - 参数表
	 * @return
	 */
    public Map<String, Object> getValueItems(Map<String, Object> map)
    {
        List<ValueItem> valueItems =
            map == null ?
            valueItemDao.getAllValueItems() : valueItemDao.getValueItems(refreshParam(map));
        return getResult(valueItems, map);
    }
    
    /**
     * 修改值增项
	 * @param variable : Variable- 值增项信息
	 */
    @Transactional
    public void update(ValueItem valueItem)
    {
        valueItemDao.update(valueItem);
    }
    
    /**
     * 根据变量id删除值增项
	 * @param id : Long - 值增项id
	 * 
	 */
    @Transactional
    public void delete(Long id)
    {
        valueItemDao.delete(id);
    }
    
    /**
     * 根据参数表获取分页信息
	 * @param map - 参数表
	 * @return
	 */
    @Override
    protected int getRows(Map<String, Object> map)
    {
        return valueItemDao.getTableCount(map);
    }

    @Autowired
    private ValueItemDao valueItemDao;
}
