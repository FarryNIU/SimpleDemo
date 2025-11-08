package com.bamboo.firstdemo.controller;

import com.bamboo.firstdemo.bean.User;
import com.bamboo.firstdemo.dao.bean.UserDB;
import com.bamboo.firstdemo.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author FarryNiu 2025/11/6
 */
@Controller
public class LoginController {
    @Autowired(required = false)
    private UserMapper userMapper;
@GetMapping("/loginGet")
    public String loginGet(User user){
        System.out.println(user.getUsername()+" "+user.getUpw());
        userMapper.addUser(new UserDB(user.getUsername(),user.getUpw()));
        return "sucess";
    }
}
