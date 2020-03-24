package service;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import abu.systemutil.SystemUtil;
import conf.Global;
import conf.GlobalBean;
import message.res.Response;

@Service
public class FileUploadService
{
	/**
	 * 文件上传
	 * @param userId : Long - 用户账号
	 * @param file : MultipartFile - 文件
	 * @return
	 */
    public Response upload(Long userId, MultipartFile file)
    {
    	// 获取文件路径
        String rootPath = getRootPath("uploadDir");
        StringBuilder partialFileName = new StringBuilder();
        //获取文件的原始名称
        String originalName = file.getOriginalFilename();
        //获取文件名中'。'最后出现的位置
        int postfixPos = originalName.lastIndexOf('.');
        String postfix = "";
        if (postfixPos >= 0)
        {
        	//从文件名中提取字符
            postfix = originalName.substring(postfixPos);
        }
        //文件名拼接
        partialFileName.append(userId).append("_").append(SystemUtil.getUID())
            .append(postfix);
        //文件名转化为String类型
        String fileName = partialFileName.toString();
        try
        {
            file.transferTo(new File(rootPath + File.separator + fileName));
        }
        catch (Exception e)
        {
            return new Response(e);
        }
        return new Response("file" + File.separator + fileName);
    }
    
    /**
	 * 获取文件路径
	 * @param type : String - 文件类型
	 * @return
	 */
    private String getRootPath(String type)
    {
        String dir = (String)GlobalBean.global.getUploadDir();
        if (dir == null)
        {
            String rootPath = "";
            //获取文件路径
            rootPath = SystemUtil.getClassRootPath(Global.class);
            if (rootPath.startsWith("/"))
            {
                rootPath = rootPath.substring(1);
            }
            dir = rootPath + "/" + type;
            try
            {
                SystemUtil.createDir(dir);
            }
            catch (Exception e)
            {
                dir = rootPath;
            }
        }
        return dir;
    }
}
