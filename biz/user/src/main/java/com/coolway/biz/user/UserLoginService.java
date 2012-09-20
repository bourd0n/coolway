package com.coolway.biz.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coolway.dao.UserMapper;
import com.coolway.entity.UserExample;

@Service
public class UserLoginService {

	@Autowired
	private UserMapper userMapper;
	
	public boolean login(String name,String password){
		boolean succ = false;
		UserExample example = new UserExample();
		example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
		int count = userMapper.countByExample(example);
		if(count>0) {
			succ  = true;
		}
		return succ;
	}
}
