package service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextAid implements ApplicationContextAware
{
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException
    {
        applicationContext = ac;
        WidgetService.dummy();
    }
    
    public static Object getBean(String beanName)
    {
        return applicationContext.getBean(beanName);
    }
    
    private static ApplicationContext applicationContext;
}
