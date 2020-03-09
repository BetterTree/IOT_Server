package controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import message.res.Response;
import po.Variable;
import service.VariableService;

/**
 * 
 * 变量控制器类
 *
 */

@RestController
@RequestMapping("/variable")
public class VariableController
{
	/**
	 * 变量新增
	 * @param variable : Variable- 变量信息
	 * @return 返回变量信息
	 */
    @RequestMapping(method=RequestMethod.POST)
    public Response add(@RequestBody Variable variable)
    {
        try
        {
            variableService.add(variable);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(variable);
    }
    
    /**
     * 根据参数表获取变量信息
	 * @param map - 参数表
	 * @return 返回变量信息
	 */
    @RequestMapping(method=RequestMethod.GET)
    public Response getVariables(@RequestParam Map<String, Object> map)
    {
        try
        {
            map = variableService.getVariables(map);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(map);
    }
    
    /**
     * 获取变量信息
	 * @param map - 参数表
	 * @return 返回变量信息
	 */
    @RequestMapping(value="all", method=RequestMethod.GET)
    public Response getVariables()
    {
        Map<String, Object> map = null;
        try
        {
            map = variableService.getVariables(null);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(map);
    }
    
    /**
     * 根据变量id获取变量信息
	 * @param id : Long - 变量id
	 * @return 返回变量信息
	 */
    @RequestMapping(value="{id}", method=RequestMethod.GET)
    public Response getVariable(@PathVariable("id") Long id)
    {
        Variable v = null;
        try
        {
            v = variableService.getVariable(id);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(v);
    }
    
    /**
     * 修改变量
	 * @param variable : Variable- 变量信息
	 * @return 返回成功应答
	 */
    @RequestMapping(method=RequestMethod.PUT)
    public Response update(@RequestBody Variable variable)
    {
        try
        {
            variableService.update(variable);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 根据变量id删除变量
	 * @param id : Long - 变量id
	 * @return 返回成功应答
	 */
    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public Response delete(@PathVariable("id") Long id)
    {
        try
        {
            variableService.delete(id);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    @Autowired
    private VariableService variableService;
}
