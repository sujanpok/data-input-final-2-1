Contr.java


package com.example.demo.cont;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.datainput.Input;
import com.example.demo.datainput.UserInfo;
import com.example.demo.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class Contr {

	
    @Autowired
    private UserMapper userMapper;
	
    @GetMapping("/user")
    public String hello(Model model) {
        return "user";
    }
    
    @RequestMapping("/regi")
    public String defectDetails() {
        return "regi";
    }
    
    
    
    
    @PostMapping("/logined")
    public String loginCheck(HttpSession session,Input request,Model model) {
    	
    	String msg;
    	
    	int i = userMapper.doCheck(request);
    	
    	if(i< 1) {
    		msg= "ない";
    	}else {
    		UserInfo userInfo =	userMapper.getUserInfo(request);
    		session.setAttribute("info", userInfo);
    		 model.addAttribute("userinfo",userInfo);
    		msg="OK";
    	}
    	 model.addAttribute("msg",msg);
    	
    	return "hello2";
    	
    }
    
    
    
    }




Input.java




package com.example.demo.datainput;

public class Input {
	private String user;
	private String pwd;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}


UserInfo.java
package com.example.demo.datainput;

import lombok.Data;

@Data
public class UserInfo {
	
	private String mail;
	
	private int age;

}


UserMapper.java


package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.datainput.Input;
import com.example.demo.datainput.UserInfo;


@Mapper
public interface  UserMapper {
	
	@Select(" select count(username) from hello where username = #{user} AND pwd = #{pwd}")
	int doCheck(Input user);

	
	@Select("select age, mail from userinfo where username = #{user}")
	UserInfo getUserInfo(Input request);
}
