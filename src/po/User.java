package po;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 用户持久化类
 *
 */
public class User
{
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserPwd()
    {
        return userPwd;
    }

    public void setUserPwd(String userPwd)
    {
        this.userPwd = userPwd;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserTel()
    {
        return userTel;
    }

    public void setUserTel(String userTel)
    {
        this.userTel = userTel;
    }

    public Integer getUserType()
    {
        return userType;
    }

    public void setUserType(Integer userType)
    {
        this.userType = userType;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public String getHabit()
    {
        return habit;
    }

    public void setHabit(String habit)
    {
        this.habit = habit;
    }

    public String getImAccount()
    {
        return imAccount;
    }

    public void setImAccount(String imAccount)
    {
        this.imAccount = imAccount;
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

    public String getUserPhoto()
    {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto)
    {
        this.userPhoto = userPhoto;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }
        User p = (User)o;
        return id == p.id;
    }
    
    @Override
    public int hashCode()
    {
        return id.intValue();
    }

    private Long id;  //用户主键
    private String userId;  //用户ID
    private String userPwd;  //用户密码
    private String userName;  //用户名称
    private String userTel;  //联系电话
    private Integer userType;  //用户类型
    private String gender;  //性别
    private String city;  //所在城市
    private Date birthday;  //生日
    private String userEmail;  //用户邮箱
    private String school;  //所在学校
    private String grade;  //所在年级
    private String habit;  //喜好
    private String imAccount;  //即时通信账号
    private String question;  //密码找回问题
    private String answer;  //密码找回回答
    private String userPhoto;  //用户头像
    private Timestamp updateTime;  //更新时间
    
    private String token;  //用户登录产生令牌
}
