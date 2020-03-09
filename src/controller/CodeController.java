package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import abu.systemutil.SystemUtil;
import message.res.Response;

@RestController
@RequestMapping("/code")
public class CodeController
{
    /**
     * 生成唯一码
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    public Response getCode()
    {
        return new Response(SystemUtil.getUID());
    }
}
