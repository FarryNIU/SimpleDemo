package com.bamboo.firstdemo.controller;

import com.bamboo.firstdemo.bean.User;
import com.bamboo.firstdemo.controller.bean.Code;
import com.bamboo.firstdemo.controller.bean.LoginRequest;
import com.bamboo.firstdemo.controller.bean.LoginRespond;
import com.bamboo.firstdemo.controller.bean.OpenidRespond;
import com.bamboo.firstdemo.dao.bean.UserDB;
import com.bamboo.firstdemo.dao.mapper.UserMapper;
import com.bamboo.firstdemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author FarryNiu 2025/11/6
 */
@Controller
public class LoginController {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private LoginService loginService;

@GetMapping("/loginGet")
    public String loginGet(User user){
        System.out.println(user.getUsername()+" "+user.getUpw());
        userMapper.addUser(new UserDB(user.getUsername(),user.getUpw()));
        return "sucess";
    }

    @PostMapping("/login")
    @ResponseBody
    public LoginRespond login(@RequestBody LoginRequest loginRequest){
        System.out.println("用户登录 "+ loginRequest);
        return loginService.login(loginRequest);
    }
}
