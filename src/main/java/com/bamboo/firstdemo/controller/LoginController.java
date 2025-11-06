package com.bamboo.firstdemo.controller;

import com.bamboo.firstdemo.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author FarryNiu 2025/11/6
 */
@Controller
public class LoginController {
@GetMapping("/loginGet")
    public String loginGet(User user){
        System.out.println(user.getUsername()+" "+user.getUpw());
        return "sucess";
    }
}
