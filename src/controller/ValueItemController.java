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
import po.ValueItem;
import service.ValueItemService;

/**
 * 
 * 值增项控制器类
 *
 */

@RestController
@RequestMapping("/valueItem")
public class ValueItemController
{
	/**
	 * 值增项新增
	 * @param variable : Variable- 值增项信息
	 * @return 返回值增项信息
	 */
    @RequestMapping(method=RequestMethod.POST)
    public Response add(@RequestBody ValueItem valueItem)
    {
        try
        {
            valueItemService.add(valueItem);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(valueItem);
    }
    
    /**
     * 根据参数表获取值增项信息
	 * @param map - 参数表
	 * @return 返回值增项信息
	 */
    @RequestMapping(method=RequestMethod.GET)
    public Response getValueItems(@RequestParam Map<String, Object> map)
    {
        try
        {
            map = valueItemService.getValueItems(map);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(map);
    }
    
    /**
     * 获取值增项信息
	 * @param map - 参数表
	 * @return 返回值增项信息
	 */
    @RequestMapping(value="all", method=RequestMethod.GET)
    public Response getValueItems()
    {
        Map<String, Object> map = null;
        try
        {
            map = valueItemService.getValueItems(null);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(map);
    }
    
    /**
     * 根据变量id获取值增项信息
	 * @param id : Long - 值增项id
	 * @return 返回值增项信息
	 */
    @RequestMapping(value="{id}", method=RequestMethod.GET)
    public Response getValueItem(@PathVariable("id") Long id)
    {
        ValueItem vi = null;
        try
        {
            vi = valueItemService.getValueItem(id);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(vi);
    }
    
    /**
     * 修改值增项
	 * @param variable : Variable- 值增项信息
	 * @return 返回成功应答
	 */
    @RequestMapping(method=RequestMethod.PUT)
    public Response update(@RequestBody ValueItem valueItem)
    {
        try
        {
            valueItemService.update(valueItem);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 根据值增项id删除值增项
	 * @param id : Long - 值增项id
	 * @return 返回成功应答
	 */
    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public Response delete(@PathVariable("id") Long id)
    {
        try
        {
            valueItemService.delete(id);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    @Autowired
    private ValueItemService valueItemService;
}
