package dao;

import java.util.List;
import java.util.Map;

import po.Project;

/**
 * 
 * 项目DAO层接口
 *
 */
public interface ProjectDao extends CountDao
{
	/**
	 * 项目新增
	 */
    void add(Project proj);  
    /**
	 * 根据参数表获取项目信息
	 */
    List<Project> getProjects(Map<String, Object> map);  
    /**
	 * 获取所有项目信息
	 */
    List<Project> getAllProjects();  
    /**
	 * 通过id获取项目信息
	 */
    Project getProjectById(Long id);  
    /**
	 * 根据参数表获取项目信息及所有子信息
	 */
    List<Project> getProjectInfo(Map<String, Object> map);  
    /**
	 * 根据id查找项目信息及所有子信息
	 */
    Project getProjectInfoById(Long id);  
    /**
	 * 项目修改
	 */
    void update(Project proj);  
    /**
	 * 根据id删除项目
	 */
    void delete(Long id);  
}
