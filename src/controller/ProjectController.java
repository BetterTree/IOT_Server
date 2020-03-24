package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import message.req.WidgetReq;
import message.res.ProjectResponse;
import message.res.Response;
import po.Project;
import service.ProjectService;

/**
 * 
 * 项目控制器类
 *
 */
@RestController
@RequestMapping("/project")
public class ProjectController
{
	/**
	 * 项目新增
	 * @param proj : Project - 项目信息
	 * @return
	 */
    @RequestMapping(method=RequestMethod.POST)
    public Response add(@RequestBody Project proj)
    {
        try
        {
            projService.add(proj);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(proj);
    }
    
    /**
     * 根据参数表获取所有项目信息
	 * @param map - 参数表
	 * @return
	 */   
    @RequestMapping(method=RequestMethod.GET)
    public Response getProjects(
        @RequestAttribute("userId") Long userId,
        @RequestParam Map<String, Object> map)
    {
        map.put("user_id", userId);
        try
        {
            map = projService.getProjects(map);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(map);
    }
    
    /**
     * 根据id查找project信息及所有子信息
     * @param id : Long - 项目id
     * @return : 匹配的project信息
     */
    @RequestMapping(value="info/{id}", method=RequestMethod.GET)
    public Response getProjectInfo(@PathVariable("id") Long id)
    {
        Response res = null;
        try
        {
            res = new Response(new ProjectResponse(projService.getProjectInfoById(id)));
        }
        catch (Exception e)
        {
            res = new Response(e);
        }
        return res;
    }
    
    /**
     * 根据参数表获取所有项目信息
	 * @param map - 参数表
	 * @return
	 */
    @RequestMapping(value="all", method=RequestMethod.GET)
    public Response getProjects(@RequestAttribute("userId") Long userId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_id", userId);
        try
        {
            map = projService.getProjects(map);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(map);
    }
    
    /**
     * 根据id获取项目信息
     * @param id : Long - 项目id
     * @return
     */
    @RequestMapping(value="{id}", method=RequestMethod.GET)
    public Response getProject(
        @RequestAttribute("userId") Long userId,
        @PathVariable("id") Long id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("user_id", userId);
        Project proj = null;
        try
        {
            proj = projService.getProject(map);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(proj);
    }
    
    /**
     * 修改项目
	 * @param proj : Project - 项目信息
	 * @return
	 */
    @RequestMapping(method=RequestMethod.PUT)
    public Response update(@RequestBody Project proj)
    {
        try
        {
            projService.update(proj);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 修改控件
	 * @param list : 封装的控件信息
	 * @return
	 */
    @RequestMapping(value="widgets", method=RequestMethod.PUT)
    public Response updateWidgets(@RequestBody List<WidgetReq> widgetReqs)
    {
        try
        {
            projService.updateWidgets(widgetReqs);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 根据项目id删除项目
	 * @param id : Long - 项目id
	 * @return
	 */
    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public Response delete(@PathVariable("id") Long id)
    {
        try
        {
            projService.delete(id);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 运行整个工程
     * @param id : Long - 项目内码
     * @return
     */
    @RequestMapping(value="run/{id}", method=RequestMethod.GET)
    public Response run(@PathVariable("id") Long id)
    {
        Response res = null;
        try
        {
            res = new Response(new ProjectResponse(projService.run(id)));
        }
        catch (Exception e)
        {
            res = new Response(e);
        }
        return res;
    }
    
    /**
     * 停止整个工程的运行
     * @param id : Long - 工程内码
     * @return
     */
    @RequestMapping(value="stop/{id}", method=RequestMethod.GET)
    public Response stop(@PathVariable("id") Long id)
    {
        projService.stop(id);
        return Response.ok;
    }
    
    @Autowired
    private ProjectService projService;
}
