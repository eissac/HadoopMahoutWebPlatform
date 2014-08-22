package com.fz.action;

import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
public class TestAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	public void test(){
		PrintWriter writer=null;
		String info="name:"+name+",email:"+email;
		System.out.println(info);
		try{
			writer= ServletActionContext.getResponse().getWriter();
			writer.write(info);//响应输出
			//释放资源，关闭流
			writer.flush();
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
