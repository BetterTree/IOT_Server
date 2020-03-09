package dao;

import java.util.List;
import java.util.Map;

import po.Widget;

/**
 * 
 * 控件DAO层接口
 *
 */
public interface WidgetDao extends CountDao
{
	/**
	 * 控件新增
	 */
    void add(Widget widget);  
    /**
	 * 根据参数表获取控件信息
	 */
    List<Widget> getWidgets(Map<String, Object> map);  
    /**
	 * 获取所有控件信息
	 */
    List<Widget> getAllWidgets();  
    /**
	 * 修改控件
	 */
    void update(Widget widget);  
    /**
	 * 根据控件id删除控件
	 */
    void delete(Long id);  
    /**
	 * 通过项目id删除控件
	 */
    void deleteByProject(Long id);  
    /**
	 * 根据参数表获取控件信息及所有子信息
	 */
    List<Widget> getWidgetInfo(Map<String, Object> map);  
    /**
	 * 根据id查找控件信息及所有子信息
	 */
    Widget getWidgetInfoById(Long id);  
    /**
	 * 根据id查找控件信息
	 */
    Widget getWidgetById(Long id);  
    /**
	 * 根据参数表查找控件信息
	 */
    Widget getWidgetByCode(Map<String, Object> map);  
}
