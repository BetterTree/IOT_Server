package controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import message.res.DeviceStatusResponse;
import message.res.Response;
import po.Device;
import service.DeviceService;

/**
 * 
 * 设备控制器类
 *
 */
@RestController
@RequestMapping("/device")
public class DeviceController
{
	/**
	 * 设备新增
	 * @param dev : Device - 设备信息
	 * @return 返回设备信息
	 */
    @RequestMapping(method=RequestMethod.POST)
    public Response add(@RequestBody Device dev)
    {
        try
        {
            devService.add(dev);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(dev);
    }
    
    /**
     * 根据参数表获取设备信息
	 * @param map - 参数表
	 * @return 返回设备信息
	 */
    @RequestMapping(method=RequestMethod.GET)
    public Response getDevices(
        @RequestAttribute("userId") Long userId,
        @RequestParam Map<String, Object> map)
    {
        map.put("user_id", userId);
        try
        {
            map = devService.getDevices(map);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(map);
    }
    
    /**
     * 获取所有设备信息
	 * @param map - 参数表
	 * @return 返回设备信息
	 */
    @RequestMapping(value="all", method=RequestMethod.GET)
    public Response getDevices(@RequestAttribute("userId") Long userId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_id", userId);
        try
        {
            map = devService.getDevices(map);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(map);
    }
    
    /**
     * 根据设备id获取设备信息
	 * @param id : Long - 设备id
	 * @return 返回设备信息
	 */
    @RequestMapping(value="{id}", method=RequestMethod.GET)
    public Response getDevice(
        @RequestAttribute("userId") Long userId,
        @PathVariable("id") Long id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("user_id", userId);
        Device dev = null;
        try
        {
            dev = devService.getDevice(map);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response(dev);
    }
    
    /**
     * 修改设备
	 * @param dev : Dev - 设备信息
	 * @return 返回成功应答
	 */
    @RequestMapping(method=RequestMethod.PUT)
    public Response update(@RequestBody Device dev)
    {
        try
        {
            devService.update(dev);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 根据id删除设备
	 * @param id : Long - 设备id
	 * @return 返回成功应答
	 */
    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public Response delete(@PathVariable("id") Long id)
    {
        try
        {
            devService.delete(id);
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return Response.ok;
    }
    
    /**
     * 根据项目识别码获取设备状态
	 * @param projCode: String - 项目识别码
	 * @return
	 */
    @RequestMapping(value="status/{projCode}", method=RequestMethod.GET)
    public Response getStatus(@PathVariable("projCode") String projCode)
    {
        Response res = null;
        try
        {
            res = new DeviceStatusResponse(devService.getDeviceStatus(projCode));
        }
        catch (Exception e)
        {
            res = new Response(e);
        }
        return res;
    }
    
    @Autowired
    private DeviceService devService;
}
