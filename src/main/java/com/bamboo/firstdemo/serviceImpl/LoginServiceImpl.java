package com.bamboo.firstdemo.serviceImpl;

import com.bamboo.firstdemo.bean.User;
import com.bamboo.firstdemo.controller.bean.LoginRequest;
import com.bamboo.firstdemo.controller.bean.LoginRespond;
import com.bamboo.firstdemo.controller.vo.UserLoginVO;
import com.bamboo.firstdemo.dao.bean.UserDB;
import com.bamboo.firstdemo.dao.mapper.UserMapper;
import com.bamboo.firstdemo.properties.JwtProperties;
import com.bamboo.firstdemo.service.LoginService;
import com.bamboo.firstdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Override
    public UserLoginVO login(LoginRequest loginRequest) {
        System.out.println("用户登录："+loginRequest.getNickName());
        User user =  userMapper.getInfo(loginRequest.getOpenid());
        if(user == null){
            System.out.println("登录失败！");
            return UserLoginVO.builder().success(0).build();
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", loginRequest.getUserId());
        claims.put("openid", loginRequest.getOpenid());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        System.out.println("生成token："+token);
        return UserLoginVO.builder()
                .userId(user.getUserId())
                .openid(user.getOpenid())
                .token(token)
                .build();
    }
}
