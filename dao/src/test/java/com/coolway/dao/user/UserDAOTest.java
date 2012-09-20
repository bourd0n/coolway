package com.coolway.dao.user;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.coolway.dao.UserMapper;
import com.coolway.entity.User;


@ContextConfiguration(locations = { "classpath:spring/beans.xml" })
public class UserDAOTest extends AbstractJUnit4SpringContextTests{

	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void testGetUser(){
		User u = new User();
		u.setName("老徐");
		u.setAddress("杭州");
		u.setCreateTime(new Date());
		u.setEmail("zhaopeng.xuzp@gmail.com");
		userMapper.insert(u);
		u.getId();
	}
	
}
