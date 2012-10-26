package com.coolway.biz.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coolway.common.model.user.UserSex;
import com.coolway.common.model.user.UserStatus;
import com.coolway.dao.UserMapper;
import com.coolway.entity.User;

/**
 * 
 * 注册用户
 *  
 * @author peng Sep 26, 2012 5:45:49 PM
 */

@Service
public class UserRegisterService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService UserService;
    
    public User register(String name,String email,UserSex sex){
        //validate param
        
        // is Exist name or email
        
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setSex(sex.name());
        u.setStatus(UserStatus.inactive.name()); // 未激活
        u.setModifyTime(new Date());
        u.setCreateTime(new Date());
        return u;
    }
    
    /**
     * 发送激活邮件给用户
     * @return
     */
    private boolean sendActiveEmail(String name,String email){
        boolean succ = false;
        
        return succ;
    }
    
}
