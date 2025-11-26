package com.bamboo.firstdemo.service;

import com.bamboo.firstdemo.controller.bean.LoginRequest;
import com.bamboo.firstdemo.controller.bean.LoginRespond;

public interface LoginService {
    LoginRespond login(LoginRequest loginRequest);
}
