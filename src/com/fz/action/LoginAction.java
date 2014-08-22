package com.fz.action;

//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fz.model.User;
import com.fz.service.user.UserService;
import com.opensymphony.xwork2.ActionSupport;
  
  
@Controller  
public class LoginAction extends ActionSupport {  
  
    private static final long serialVersionUID = 1L;  
  
    @Resource  
    private UserService userService;  
      
    private String username;  
    private String password;  
      
    public String login(){  
          
        HttpServletRequest request = ServletActionContext.getRequest();  
        User user = userService.findUserByNameAndPassword(username, password);  
        if (user != null) {  
            request.setAttribute("username", username);  
            return SUCCESS;  
        } else {  
            return ERROR;  
        }  
              
    }  
    
    /*public void getAllUsers(){
    	try {
			PrintWriter writer= ServletActionContext.getResponse().getWriter();
			Map map = new HashMap();
			List emps=userService.findAllList();
			
			map.put("rows", emps);
			map.put("total", emps.size());
			//2.将map序列化成JSON，SerializerFeature设置成DisableCircularReferenceDetect,打开循环引用检测
			String jsonString = JSON.toJSONStringWithDateFormat(map,"yyyy-MM-dd",SerializerFeature.DisableCircularReferenceDetect);
			writer.write(jsonString);//响应输出
			//释放资源，关闭流
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }*/
    public void getAllUser(){
    	try {
			PrintWriter writer= ServletActionContext.getResponse().getWriter();
			List<User> users=userService.findAllList();
	                //1.这一步必须
			String jsonString = JSON.toJSONStringWithDateFormat(users,"yyyy-MM-dd hh:mm:ss",SerializerFeature.DisableCircularReferenceDetect);
			
			writer.write(jsonString);//响应输出
			//释放资源，关闭流
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public String getUsername() {  
        return username;  
    }  
    public void setUsername(String username) {  
        this.username = username;  
    }  
    public String getPassword() {  
        return password;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    }  
      
}  