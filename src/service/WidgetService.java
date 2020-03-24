package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import abu.callback.CallBack;
import abu.systemutil.SystemUtil;
import dao.ValueItemDao;
import dao.VariableDao;
import dao.WidgetDao;
import engine.Engine;
import exception.NotExistException;
import exception.ParamMissedException;
import message.req.WidgetReq;
import po.ValueItem;
import po.Variable;
import po.Widget;

/**
 * 
 * 控件Service接口实现类
 * 包含了控件新增、根据控件id获取控件信息、根据项目id和控件识别码获取控件信息、根据参数表获取控件信息、修改控件、根据控件id删除控件、根据参数表获取分页信息、根据项目id启动项目、根据项目id停止项目
 */

@Service
public class WidgetService extends PagingService
{
	/**
	 * 控件新增
	 * @param projId : Long - 项目id
	 * @param widgetReq : WidgetReq - 封装的控件信息
	 */
    @Transactional
    public Widget add(Long projId, WidgetReq widgetReq)
    {
        Widget widget = handle(projId, widgetReq);
        // 插入控件表
        widgetDao.add(widget);
        List<Variable> varList = widget.getVariables();
        List<ValueItem> viList = null;
        if (varList != null)
        {
            for (Variable variable : varList)
            {
                // 插入变量表
                variableDao.add(variable);
                viList = variable.getValueItems();
                if (viList != null)
                {
                    for (ValueItem valueItem : viList)
                    {
                        // 插入变量值增项表
                        valueItemDao.add(valueItem);
                    }
                }
            }
        }
        return widget;
    }
    
    /**
     * 根据控件id获取控件信息
	 * @param id : Long - 控件id
	 * @return
	 */
    public Widget getWidgetById(Long id)
    {
        return widgetDao.getWidgetById(id);
    }
    
    /**
     * 根据项目id和控件识别码获取控件信息
	 * @param projId : Long - 项目id
	 * @param code : String - 控件识别码
	 * @return
	 */
    public Widget getWidgetByCode(Long projId, String code)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projId);
        map.put("code", code);
        return widgetDao.getWidgetByCode(map);
    }
    
    /**
     * 根据参数表获取控件信息
	 * @param map - 参数表
	 * @return
	 */
    public Map<String, Object> getWidgets(Map<String, Object> map)
    {
        List<Widget> widgets =
            map == null ?
            widgetDao.getAllWidgets() : widgetDao.getWidgets(refreshParam(map));
        return getResult(widgets, map);
    }
    
    /**
     * 修改控件
	 * @param req : WidgetReq - 封装的控件信息
	 */
    @Transactional
    public void update(WidgetReq req)
    {
        Long widgetId = req.getId();
        if (widgetId == null || widgetId == 0)
        {
            throw new ParamMissedException("缺失控件id参数");
        }
        
        Widget widget = new Widget();
        Variable variable = new Variable();
        
        widget.setId(widgetId);
        String code = req.getCode();
        if (code != null)
        {
        	// 如果识别码不为空，则赋值到控件类里
            widget.setCode(code);
        }
        String name = req.getName();
        if (name != null)
        {
        	// 如果控件名不为空，则赋值到控件类里
            widget.setName(name);
            // 如果控件名不为空，则赋值到变量类里
            variable.setName(name);
        }
        Integer type = req.getType();
        if (type != null)
        {
            widget.setType(type);
            variable.setType(convertWtype2Vtype(type));
            if (Widget.typeController(type))
            {
                variable.setAim(Variable.AIM_CONTROL);
            }
            else
            {
                variable.setAim(Variable.AIM_VIEW);
            }
        }
        widget.setPositionX(req.getPositionX());
        // 修改控件信息
        widgetDao.update(widget);
        String value = req.getValue();
        if (value != null)
        {
            variable.setValue(value);
        }
        String unit = req.getUnit();
        if (unit != null)
        {
            variable.setUnit(unit);
        }
        variable.setWidget(widget);
        // 统一修改指定控件的所有变量信息
        variableDao.updateByWidget(variable);
        // 删除所有的值增项信息，重新新增map中的信息
        Map<String, Object> map = req.getMap();
        if (map != null && map.size() > 0)
        {
            valueItemDao.deleteByWidget(widgetId);
            ValueItem vi = null;
            for (Entry<String, Object> entry : map.entrySet())
            {
                vi = new ValueItem();
                vi.setKey(entry.getKey());
                vi.setValue(entry.getValue().toString());
                vi.setVariable(variable);
                // 插入变量值增项表
                valueItemDao.add(vi);
            }
        }
    }
    
    /**
     * 根据控件id删除控件
	 * @param id : Long - 控件id
	 */
    @Transactional
    public void delete(Long id)
    {
        // 先删除值增项表
        valueItemDao.deleteByWidget(id);
        // 再删除变量表
        variableDao.deleteByWidget(id);
        // 最后删除控件表
        widgetDao.delete(id);
    }
    
    /**
     * 根据参数表获取分页信息
	 * @param map - 参数表
	 * @return
	 */
    @Override
    protected int getRows(Map<String, Object> map)
    {
        return widgetDao.getTableCount(map);
    }

    /**
     * 根据项目id启动项目
	 * @param id : Long - 控件id
	 * @return
	 */
    public Widget run(Long id) throws Exception
    {
        // 获取工程信息
        Widget widget = widgetDao.getWidgetInfoById(id);
        if (widget == null)
        {
            throw new NotExistException("该控件不存在");
        }
        Engine.regInstance(widget);
        return widget;
    }
   
    /**
	 * @param projCode : String - 项目识别码
	 * @param widgetCode : String - 控件识别码
	 * @param key : String - 值增项键
	 */
    public void command(String projCode, String widgetCode, String key) throws Exception
    {
        // 向引擎发起指令下达动作
        Engine.command(projCode, widgetCode, key);
    }
    
    /**
     * 根据项目id停止项目
	 * @param id : Long - 控件id
	 * @return
	 */
    public void stop(Long id)
    {
    	// 获取工程信息
        Widget widget = widgetDao.getWidgetById(id);
        if (widget != null)
        {
            Engine.delInstance(widget);
        }
    }
    
    @Autowired
    private WidgetDao widgetDao;
    @Autowired
    private VariableDao variableDao;
    @Autowired
    private ValueItemDao valueItemDao;
    
    // 服务启动时立刻加载静态内容
    public static void dummy() {}
    
    /**
     * 将封装的控件转化为控件持久化类
	 * @param id : Long - 项目id
	 * @param widgetReq : WidgetReq - 封装的控件
	 * @return
	 */
    private Widget handle(Long projId, WidgetReq widgetReq)
    {
        Widget widget = new Widget(projId, widgetReq);
        getHandler(widget).call(widget, widgetReq);
        return widget;
    }
    
    /**
     * 获取处理器
	 * @param widget : Widget - 控件
	 * @return
	 */
    private CallBack getHandler(Widget widget)
    {
        CallBack callback = null;
        // 首先直接从map中获取处理器
        callback = map.get(widget.getType());
        if (callback == null)
        {
            // 如果得不到，则根据类型分类获取
            callback = map.get(widget.typeCategory());
            if (callback == null)
            {
                // 如果得不到，使用默认处理器
                callback = defaultCallback;
            }
        }
        return callback;
    }
    
    private static CallBack defaultCallback = new CallBack()
    {
        @Override
        public Object call(Object... args)
        {
            Widget widget = (Widget)args[0];
            WidgetReq req = (WidgetReq)args[1];
            
            Variable v = new Variable();
            v.setCode(SystemUtil.getUID());
            v.setName(widget.getName());
            v.setType(convertWtype2Vtype(widget.getType()));
            v.setValue(req.getValue());
            v.setUnit(req.getUnit());
            if(widget.typeController())
            {
                v.setAim(Variable.AIM_CONTROL);
            }
            else
            {
                v.setAim(Variable.AIM_VIEW);
            }
            v.setWidget(widget);
            widget.addVariable(v);
            
            Map<String, Object> map = req.getMap();
            if (map != null && map.size() > 0)
            {
                v.setValueItems(new ArrayList<ValueItem>());
                ValueItem vi = null;
                for (Entry<String, Object> entry : map.entrySet())
                {
                    vi = new ValueItem();
                    vi.setKey(entry.getKey());
                    vi.setValue(entry.getValue().toString());
                    vi.setVariable(v);
                    v.addValueItem(vi);
                }
            }
            return null;
        }
    };
    
    private static Map<Integer, CallBack> map = new HashMap<Integer, CallBack>(); static
    {
        map.put(Widget.TYPE_NUMERIC, defaultCallback);
        map.put(Widget.TYPE_BOOLEAN, defaultCallback);
        map.put(Widget.TYPE_DATETIME, defaultCallback);
        map.put(Widget.TYPE_SWITCH, defaultCallback);
        map.put(Widget.TYPE_MULTIFUN, defaultCallback);
        map.put(Widget.TYPE_DIRECTION, defaultCallback);
        map.put(Widget.TYPE_SCROLLBAR, defaultCallback);
    }
    
    private static int convertWtype2Vtype(int wtype)
    {
        int vtype;
        switch (wtype)
        {
        case Widget.TYPE_NUMERIC:
            // 因为客户定义的需求文档中的数值类显示，包含数字和字符串，所以统一设定为字符串类型
            vtype = Variable.TYPE_STRING;
            break;
        case Widget.TYPE_BOOLEAN:
            vtype = Variable.TYPE_BOOLEAN;
            break;
        case Widget.TYPE_DATETIME:
            vtype = Variable.TYPE_DATETIME;
            break;
        case Widget.TYPE_SWITCH:
            vtype = Variable.TYPE_DICT;
            break;
        case Widget.TYPE_MULTIFUN:
            vtype = Variable.TYPE_DICT;
            break;
        case Widget.TYPE_DIRECTION:
            vtype = Variable.TYPE_DICT;
            break;
        case Widget.TYPE_SCROLLBAR:
            vtype = Variable.TYPE_DICT;
            break;
        default:
            vtype = Variable.TYPE_DICT;
            break;
        }
        return vtype;
    }
}
