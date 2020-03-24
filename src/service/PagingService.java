package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abu.systemutil.SystemUtil;

/**
 * 
 * 分页Sevice接口实现类
 *
 */

public abstract class PagingService
{
    /**
     * 将map中分页相关的参数page,rows值类型从String变更为Integer，且计算出偏移量offset参数，新增至map中
     */
    protected Map<String, Object> refreshParam(Map<String, Object> map, String... keys)
    {
        if (map != null)
        {
            // 处理分页参数（内部参数）
            // 刷新page参数（第几页）
            Integer page = refreshIntParam(map, "page", 1);  // 默认第一页
            // 刷新rows参数（每页显示行数）
            Integer rows = refreshIntParam(map, "rows", 10); // 默认显示10行
            if (page != null && rows != null)
            {
                // 计算分页的偏移量，并放到map中
                int offset = (page - 1) * rows;
                map.put("offset", offset);
            }
            // 处理外部参数
            for (String k : keys)
            {
                refreshIntParam(map, k);
            }
        }
        
        return map;
    }
    
    /**
     * 获取总记录数
     * @param list - 原始的po数据
     * @param map - 参数表
     * @return
     */
    public int getCount(List<?> list, Map<String, Object> map)
    {
        int count = 0;
        if (list != null)
        {
            if (map == null)
            {
                count = list.size();
            }
            else
            {
                if (map.get("offset") == null)
                {
                    // 不需要分页，list中就是所有数据
                    count = list.size();
                }
                else
                {
                    // 需要分页，list只保存当前页数据，需要额外获得总记录数
                    count = getRows(map);
                    if (count < list.size())
                    {
                        count = list.size();
                    }
                }
            }
        }
        return count;
    }
    
    /**
     * 保持老方案，返回的是原始的po数据
     * @param list - 原始的po数据
     * @param map - 参数表
     * @return
     */
    protected Map<String, Object> getResult(List<?> list, Map<String, Object> map)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        int count = getCount(list, map);
        result.put("total", count);
        result.put("rows", list);
        return result;
    }
    
    /**
     * 将值类型变更为Integer
     * @param map - 参数表
     * @param key : String - 键
     * @return
     */
    private Integer refreshIntParam(Map<String, Object> map, String key)
    {
        if (map == null || map.size() == 0)
        {
            return null;
        }
        Integer result = null;
        // 从map中获得指定参数的值，并将值类型从String变更为Integer
        Object v = map.get(key);
        if (v != null)
        {
            if (v instanceof String)
            {
                result = SystemUtil.string2Int((String)v);
                map.put(key, result);
            }
        }
        return result;
    }
    
    /**
     * 将值类型变更为Integer
     * @param map - 参数表
     * @param key : String - 键
     * @param defaultValue : Integer - 默认值
     * @return
     */
    private Integer refreshIntParam(Map<String, Object> map, String key, Integer defaultValue)
    {
        if (map == null || map.size() == 0)
        {
            return null;
        }
        Integer result = null;
        // 从map中获得指定参数的值，并将值类型从String变更为Integer
        Object v = map.get(key);
        if (v != null)
        {
            if (v instanceof String)
            {
                result = SystemUtil.string2Int((String)v, defaultValue);
            }
            else
            {
                result = defaultValue;
            }
            map.put(key, result);
        }
        return result;
    }
    
    protected abstract int getRows(Map<String, Object> map);
}
