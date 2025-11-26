package com.bamboo.firstdemo.serviceImpl;

import com.bamboo.firstdemo.controller.bean.LoginRequest;
import com.bamboo.firstdemo.controller.bean.LoginRespond;
import com.bamboo.firstdemo.service.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public LoginRespond login(LoginRequest loginRequest) {
        return null;
    }
}
