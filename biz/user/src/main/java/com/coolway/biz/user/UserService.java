package com.coolway.biz.user;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coolway.common.model.user.UserStatus;
import com.coolway.dao.UserMapper;
import com.coolway.entity.User;
import com.coolway.entity.UserExample;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    public User getUserById(int userId){
        return userMapper.selectByPrimaryKey(userId);
    }
    
    public boolean isExistName(String name){
        boolean exist = false;
        if(StringUtils.isBlank(name)){
            throw new IllegalArgumentException("name is blank");
        }
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name).andStatusEqualTo(UserStatus.active.name());
        int count = userMapper.countByExample(example);
        if(count > 0){
            exist = true;
        }
        return exist;
    }
    
    public boolean isExistEmail(String email){
        boolean exist = false;
        if(StringUtils.isBlank(email)){
            throw new IllegalArgumentException("email is blank");
        }
        UserExample example = new UserExample();
        example.createCriteria().andEmailEqualTo(email).andStatusEqualTo(UserStatus.active.name());
        int count = userMapper.countByExample(example);
        if(count > 0){
            exist = true;
        }
        return exist;
    }

}
