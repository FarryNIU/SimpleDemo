package com.bamboo.firstdemo.service;

import com.bamboo.firstdemo.controller.bean.LoginRequest;
import com.bamboo.firstdemo.controller.bean.LoginRespond;
import com.bamboo.firstdemo.controller.vo.UserLoginVO;

public interface LoginService {
    UserLoginVO login(LoginRequest loginRequest);
}
