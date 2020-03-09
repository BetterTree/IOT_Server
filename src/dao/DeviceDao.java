package dao;

import java.util.List;
import java.util.Map;

import po.Device;

/**
 * 
 * 设备DAO接口
 *
 */

public interface DeviceDao extends CountDao
{
	/**
	 * 设备新增
	 */
    void add(Device dev);
    /**
	 * 根据参数表获取设备信息
	 */
    List<Device> getDevices(Map<String, Object> map);
    /**
	 * 获取所有设备信息
	 */
    List<Device> getAllDevices();
    /**
	 * 根据id获取设备信息
	 */
    Device getDeviceById(Long id);
    /**
	 * 项目修改
	 */
    void update(Device dev);
    /**
	 * 根据id删除项目
	 */
    void delete(Long id);
    /**
	 * 根据项目id删除项目
	 */
    void deleteByProject(Long id);
}
