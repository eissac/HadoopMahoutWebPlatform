package generate.data;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fz.model.User;
import com.fz.service.user.UserService;
import com.fz.util.SpringUtil;


public class GenerateData {
	private UserService userService =null;
	
	@Before
	public void setup(){
		userService = (UserService)SpringUtil.getBean("userService");
	}
	@SuppressWarnings("deprecation")
	@Test // generate user data
	public void generateData(){
		User user= new User();
		user.setAddress("home at home");
		user.setCreateTime(new Date(2014-1900,5,30,10,22,11));
		user.setPassword("test1");
		user.setPhoneNumber("9123309");
		user.setUpdateTime(new Date(2014-1900,6,3,10,22,11));
		user.setUserName("test1");
		userService.saveUser(user);
	}
}
