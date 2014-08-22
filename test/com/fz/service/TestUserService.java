package com.fz.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fz.model.User;
import com.fz.service.user.UserService;
import com.fz.util.SpringUtil;

public class TestUserService {
	private UserService us=null;
	@Before
	public void init(){
		us= (UserService) SpringUtil.getBean("userService");
	}
	
	@Test
	public void test(){
		List<User> users= us.findAllList();
		System.out.println(users.size());
	}
}
