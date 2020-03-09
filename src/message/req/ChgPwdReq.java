package message.req;

/**
 * 调用修改密码接口的request请求对象
 * 
 * @author Bruce
 *
 */
public class ChgPwdReq
{
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }
    
    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public String getNewPwd()
    {
        return newPwd;
    }

    public void setNewPwd(String newPwd)
    {
        this.newPwd = newPwd;
    }

    private Long id;
    private String question;
    private String answer;
    private String newPwd;
}
