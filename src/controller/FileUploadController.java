package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import message.res.Response;
import service.FileUploadService;

/**
 * 
 * 文件上传控制器类
 *
 */
@RestController
@RequestMapping(value="/upload")
public class FileUploadController
{
	/**
	 * 文件上传
	 * @param file : MultipartFile - 文件
	 * @return
	 */
    @RequestMapping(method=RequestMethod.POST)
    public Response upload(
        @RequestAttribute("userId") Long userId,
        @RequestParam(value="file") MultipartFile file)
    {
        Response res = null;
        try
        {
            res = uploadService.upload(userId, file);
        }
        catch (Exception e)
        {
            res = new Response(e);
        }
        return res;
    }
    
    @Autowired
    private FileUploadService uploadService;
}
