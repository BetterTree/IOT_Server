package service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import abu.systemutil.SystemUtil;
import dao.DeviceDao;
import dao.ProjectDao;
import dao.ValueItemDao;
import dao.VariableDao;
import dao.WidgetDao;
import engine.Engine;
import exception.NotExistException;
import message.req.WidgetReq;
import po.Project;

/**
 * 
 * 项目Service接口实现类
 * 包含了项目新增、根据参数表获取单个项目信息、根据参数表获取所有项目信息、修改项目、修改控件、根据项目id删除项目、根据参数表获取项目信息、根据参数表获取分页信息、根据项目id获取项目信息、根据项目id启动项目、根据项目id停止项目
 */
@Service
public class ProjectService extends PagingService
{
	/**
	 * 项目新增
	 * @param proj : Project - 项目信息
	 * 
	 */
    @Transactional
    public void add(Project proj)
    {
        // 如果不携带编码，则自动生成唯一码
        String code = proj.getCode();
        if (code == null || code.isEmpty())
        {
            proj.setCode(SystemUtil.getUID());
        }
        projDao.add(proj);
    }
    
    /**
     * 根据参数表获取单个项目信息
	 * @param map - 参数表
	 * @return
	 */
    public Project getProject(Map<String, Object> map)
    {
        List<Project> projList = projDao.getProjects(map);
        if (projList == null || projList.size() == 0)
        {
            return null;
        }
        return projList.get(0);
    }
    
    /**
     * 根据参数表获取所有项目信息
	 * @param map - 参数表
	 * @return
	 */
    public Map<String, Object> getProjects(Map<String, Object> map)
    {
        List<Project> projs =
            map == null ?
            projDao.getAllProjects() : projDao.getProjects(refreshParam(map));
        return getResult(projs, map);
    }
    
    /**
     * 修改项目
	 * @param proj : Project - 项目信息
	 * 
	 */
    @Transactional
    public void update(Project proj)
    {
        projDao.update(proj);
    }
    
    /**
     * 修改控件
	 * @param list : 封装的控件信息
	 * 
	 */
    @Transactional
    public void updateWidgets(List<WidgetReq> widgetReqs)
    {
        for (WidgetReq req : widgetReqs)
        {
            widgetService.update(req);
        }
    }
    
    /**
     * 根据项目id删除项目
	 * @param id : Long - 项目id
	 */
    @Transactional
    public void delete(Long id)
    {
        // 先删除值增项表
        valueItemDao.deleteByProject(id);
        // 再删除变量表
        variableDao.deleteByProject(id);
        // 最后删除控件表
        widgetDao.deleteByProject(id);
        
        // 删除设备表
        deviceDao.deleteByProject(id);
        
        // 删除项目本身
        projDao.delete(id);
    }
    
    /**
     * 根据参数表获取分页信息
	 * @param map - 参数表
	 * @return
	 */
    @Override
    protected int getRows(Map<String, Object> map)
    {
        return projDao.getTableCount(map);
    }
    
    /**
     * 根据项目id获取项目信息
	 * @param id : Long - 项目id
	 * @return
	 */
    public Project getProjectInfoById(Long id)
    {
        return projDao.getProjectInfoById(id);
    }
    
    /**
     * 根据项目id启动项目
	 * @param id : Long - 项目id
	 * @return
	 */
    public Project run(Long id) throws Exception
    {
        // 获取工程信息
        Project proj = projDao.getProjectInfoById(id);
        if (proj == null)
        {
            throw new NotExistException("项目不存在");
        }
        Engine.regInstance(proj);
        return proj;
    }
    
    /**
     * 根据项目id停止项目
	 * @param id : Long - 项目id
	 * 
	 */
    public void stop(Long id)
    {
    	// 获取工程信息
        Project proj = projDao.getProjectById(id);
        if (proj != null)
        {
            Engine.delInstance(proj);
        }
    }

    @Autowired
    private ProjectDao projDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private WidgetDao widgetDao;
    @Autowired
    private VariableDao variableDao;
    @Autowired
    private ValueItemDao valueItemDao;
    @Autowired
    private WidgetService widgetService;
}
