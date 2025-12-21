package com.bamboo.firstdemo.controller;

import com.bamboo.firstdemo.bean.User;
import com.bamboo.firstdemo.controller.bean.*;
import com.bamboo.firstdemo.controller.vo.UserLoginVO;
import com.bamboo.firstdemo.controller.vo.UserLogupVO;
import com.bamboo.firstdemo.dao.bean.UserDB;
import com.bamboo.firstdemo.dao.mapper.UserMapper;
import com.bamboo.firstdemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @author FarryNiu 2025/11/6
 */
@Controller
public class LoginController {


    @Autowired(required = false)
    private LoginService loginService;


    @PostMapping("/login")
    @ResponseBody
    public UserLoginVO login(@RequestBody LoginRequest loginRequest){
        System.out.println("用户登录Controller被调用 "+ loginRequest.getNickName());
        return loginService.login(loginRequest);
    }

    @PostMapping("/logup")
    @ResponseBody
    public UserLogupVO logup(@RequestBody LogupRequest logupRequest){
        System.out.println("用户注册Controller被调用 "+ logupRequest.getNickName());return loginService.logup(logupRequest);
    }

 /*   @PostMapping("/login")
    @ResponseBody
    public UserLoginVO test(@RequestBody LoginRequest loginRequest){
        System.out.println("用户登录 "+ loginRequest);
        return loginService.login(loginRequest);
    }*/
}
