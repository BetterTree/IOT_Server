package service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import abu.systemutil.SystemUtil;
import dao.DeviceDao;
import engine.Engine;
import po.Device;

/**
 * 
 * 设备Service接口实现类
 * 包含了设备新增、根据参数表获取单个设备信息、根据参数表获所有取设备信息、根据项目识别码获取设备状态、修改设备、根据id删除设备、根据参数表获取分页信息
 */
@Service
public class DeviceService extends PagingService
{
	/**
	 * 设备新增
	 * @param dev : Device - 设备信息
	 * 
	 */
    @Transactional
    public void add(Device dev)
    {
        // 如果不携带编码，则自动生成唯一码
        String code = dev.getCode();
        if (code == null || code.isEmpty())
        {
            dev.setCode(SystemUtil.getUID());
        }
        devDao.add(dev);
    }
    
    /**
     * 根据参数表获取单个设备信息
	 * @param map - 参数表
	 * @return 匹配的设备信息
	 */
    public Device getDevice(Map<String, Object> map)
    {
    	// 获取设备信息
        List<Device> devList = devDao.getDevices(map);
        if (devList == null || devList.size() == 0)
        {
            return null;
        }
        return devList.get(0);
    }
    
    /**
     * 根据参数表获所有取设备信息
	 * @param map - 参数表
	 * @return
	 */
    public Map<String, Object> getDevices(Map<String, Object> map)
    {
        List<Device> devs =
            map == null ?
            devDao.getAllDevices() : devDao.getDevices(refreshParam(map));
        return getResult(devs, map);
    }
    
    /**
     * 根据项目识别码获取设备状态
	 * @param projCode: String - 项目识别码
	 * @return
	 */
    public List<Device> getDeviceStatus(String projCode)
    {
        return Engine.getDevices(projCode);
    }
    
    /**
     * 修改设备
	 * @param dev : Dev - 设备信息
	 * 
	 */
    @Transactional
    public void update(Device dev)
    {
        devDao.update(dev);
    }
    
    /**
     * 根据id删除设备
	 * @param id : Long - 设备id
	 * 
	 */
    @Transactional
    public void delete(Long id)
    {
        devDao.delete(id);
    }
    
    /**
     * 根据参数表获取分页信息
	 * @param map - 参数表
	 * @return
	 */
    @Override
    protected int getRows(Map<String, Object> map)
    {
        return devDao.getTableCount(map);
    }

    @Autowired
    private DeviceDao devDao;
}
