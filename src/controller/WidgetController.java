package controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exception.ForbiddenException;
import exception.NotExistException;
import message.req.WidgetReq;
import message.res.Response;
import message.res.WidgetResponse;
import po.Widget;
import service.WidgetService;

/**
 * 
 * 控件控制器类
 *
 */

@RestController
@RequestMapping("/widget")
public class WidgetController
{
	/**
	 * 控件新增
	 * @param projId : Long - 项目id
	 * @param widgetReq : WidgetReq - 封装的控件信息
	 * @return 返回控件信息
	 */
    @RequestMapping(value="{projectId}", method=RequestMethod.POST)
    public Response add(
        @PathVariable("projectId") Long projectId,
        @RequestBody WidgetReq widgetReq)
    {
        Widget widget = null;
        try
        {
            widget = widgetService.add(projectId, widgetReq);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(new WidgetResponse(widget));
    }
    
    /**
     * 根据参数表获取控件信息
	 * @param map - 参数表
	 * @return 返回控件信息
	 */
    @RequestMapping(method=RequestMethod.GET)
    public Response getWidgets(@RequestParam Map<String, Object> map)
    {
        try
        {
            map = widgetService.getWidgets(map);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(map);
    }
    
    /**
     * 获取控件信息
	 * @return 返回控件信息
	 */
    @RequestMapping(value="all", method=RequestMethod.GET)
    public Response getWidgets()
    {
        Map<String, Object> map = null;
        try
        {
            map = widgetService.getWidgets(null);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(map);
    }
    
    /**
     * 根据控件id获取控件信息
	 * @param id : Long - 控件id
	 * @return 返回控件信息
	 */
    @RequestMapping(value="{id}", method=RequestMethod.GET)
    public Response getWidget(@PathVariable("id") Long id)
    {
        Widget w = null;
        try
        {
            w = widgetService.getWidgetById(id);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(w);
    }
    
    /**
     * 根据指定项目的控件识别码获取控件信息
     * @param projectId : Long - 指定项目
     * @param code : String - 指定控件识别码（待确认是否重复）
     * @return 成功返回控件信息，否则返回控件不存在的错误信息
     */
    @RequestMapping(value="code/{projectId}/{code}", method=RequestMethod.GET)
    public Response getWidgetByCode(
        @PathVariable("projectId") Long projectId,
        @PathVariable("code") String code)
    {
        Response res = null;
        Widget widget = widgetService.getWidgetByCode(projectId, code);
        if (widget != null)
        {
            res = new Response(new WidgetResponse(widget));
        }
        else
        {
            res = new Response(new NotExistException("指定控件不存在"));
        }
        return res;
    }
    
    /**
     * 判断指定项目的控件识别码是否已经存在
     * @param projectId : Long - 指定项目
     * @param code : String - 指定控件识别码（待确认是否重复）
     * @return 成功应答表示识别码不存在，可以正常使用；失败应答表示识别码已存在，不能使用
     */
    @RequestMapping(value="check/{projectId}/{code}", method=RequestMethod.GET)
    public Response checkCode(
        @PathVariable("projectId") Long projectId,
        @PathVariable("code") String code)
    {
        Widget widget = widgetService.getWidgetByCode(projectId, code);
        if (widget == null)
        {
            return new Response();
        }
        else
        {
            return new Response(new ForbiddenException("控件识别码已存在，请更换新的代码"));
        }
    }
    
    /**
     * 修改控件
	 * @param req : WidgetReq - 封装的控件信息
	 * @return 返回成功应答
	 */
    @RequestMapping(method=RequestMethod.PUT)
    public Response update(@RequestBody WidgetReq widgetReq)
    {
        try
        {
            widgetService.update(widgetReq);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 根据控件id删除控件
	 * @param id : Long - 控件id
	 * @return 返回成功应答
	 */
    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public Response delete(@PathVariable("id") Long id)
    {
        try
        {
            widgetService.delete(id);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 运行单个控件
     * @param id : Long - 控件内码
     * @return
     */
    @RequestMapping(value="run/{id}", method=RequestMethod.GET)
    public Response run(@PathVariable("id") Long id)
    {
        Response res = null;
        try
        {
            res = new Response(new WidgetResponse(widgetService.run(id)));
        }
        catch (Exception e)
        {
            res = new Response(e);
        }
        return res;
    }

    /**
     * 执行控制类控件的下达指令
     * @param projCode : String - 项目识别码
     * @param widgetCode : String - 控件识别码
     * @param key : String - 控件指定部位的键名
     * @return 执行成功返回成功应答，否则失败应答
     */
    @RequestMapping(value="cmd/{projCode}/{widgetCode}/{key}", method=RequestMethod.GET)
    public Response command(
        @PathVariable("projCode") String projCode,
        @PathVariable("widgetCode") String widgetCode,
        @PathVariable("key") String key)
    {
        try
        {
            widgetService.command(projCode, widgetCode.toUpperCase(), key);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 停止单个控件
     * @param id : Long - 控件内码
     * @return 返回成功应答
     */
    @RequestMapping(value="stop/{id}", method=RequestMethod.GET)
    public Response stop(@PathVariable("id") Long id)
    {
        widgetService.stop(id);
        return Response.ok;
    }
    
    @Autowired
    private WidgetService widgetService;
}
