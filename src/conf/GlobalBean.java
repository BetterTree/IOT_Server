package conf;

import service.ApplicationContextAid;

/**
 * 
 * @author Bruce
 *
 */
public class GlobalBean
{
	/**
	 * 获取global配置项
	 */
    public static Global global = (Global)ApplicationContextAid.getBean("global");
}
