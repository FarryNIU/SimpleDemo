package com.bamboo.firstdemo.service;

import com.bamboo.firstdemo.controller.bean.LoginRequest;
import com.bamboo.firstdemo.controller.bean.LoginRespond;
import com.bamboo.firstdemo.controller.bean.LogupRequest;
import com.bamboo.firstdemo.controller.vo.UserLoginVO;
import com.bamboo.firstdemo.controller.vo.UserLogupVO;

public interface LoginService {
    UserLoginVO login(LoginRequest loginRequest);

    UserLogupVO logup(LogupRequest logupRequest);
}
