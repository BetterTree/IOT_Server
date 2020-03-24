package conf;

/**
 * global实体类，对应config包中global.properties文件里的各种配置项
 * 
 * @author Bruce
 *
 */
public class Global
{
    public String getMqttUrl()
    {
        return mqttUrl;
    }
    
    public void setMqttUrl(String url)
    {
        mqttUrl = url;
    }
    
    public String getMqttUser()
    {
        return mqttUser;
    }

    public void setMqttUser(String mqttUser)
    {
        this.mqttUser = mqttUser;
    }

    public String getMqttPwd()
    {
        return mqttPwd;
    }

    public void setMqttPwd(String mqttPwd)
    {
        this.mqttPwd = mqttPwd;
    }

    public String getUploadDir()
    {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir)
    {
        this.uploadDir = uploadDir;
    }
    
    /** mqtt协议地址**/
    private String mqttUrl;
    /** mqtt协议用户名**/
    private String mqttUser;
    /** mqtt协议密码**/
    private String mqttPwd;
    /** web文件上传路径**/
    private String uploadDir;
}
