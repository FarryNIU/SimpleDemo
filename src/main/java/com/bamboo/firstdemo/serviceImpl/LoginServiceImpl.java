package com.bamboo.firstdemo.serviceImpl;

import com.bamboo.firstdemo.bean.User;
import com.bamboo.firstdemo.controller.bean.LoginRequest;
import com.bamboo.firstdemo.controller.bean.LoginRespond;
import com.bamboo.firstdemo.controller.bean.LogupRequest;
import com.bamboo.firstdemo.controller.vo.UserLoginVO;
import com.bamboo.firstdemo.controller.vo.UserLogupVO;
import com.bamboo.firstdemo.dao.bean.UserDB;
import com.bamboo.firstdemo.dao.mapper.UserMapper;
import com.bamboo.firstdemo.properties.JwtProperties;
import com.bamboo.firstdemo.service.LoginService;
import com.bamboo.firstdemo.util.AlgorithmUtil;
import com.bamboo.firstdemo.util.CacheService;
import com.bamboo.firstdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired
    private CacheService cacheService;


    /**
     * 用户登录
     * @param loginRequest
     * @return
     */
    @Override
    public UserLoginVO login(LoginRequest loginRequest) {
        System.out.println("用户登录："+loginRequest.getNickName());
        User user =  userMapper.getInfo(loginRequest.getOpenid());
        if(user == null){
            System.out.println("登录失败！");
            return UserLoginVO.builder().success(0).build();
        }
        if(loginRequest.getPassword() == null){
            System.out.println("密码为空");
            return UserLoginVO.builder().success(0).errorInfo("密码为空").build();
        }
        if(!user.getPassword().equals(AlgorithmUtil.getSHACal(loginRequest.getPassword()))){
            System.out.println("密码错误");
            return UserLoginVO.builder().success(0).errorInfo("密码错误").build();
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", loginRequest.getUserId());
        claims.put("openid", loginRequest.getOpenid());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        System.out.println("生成token："+token);

        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("userId",user.getUserId());
        userInfo.put("phone",user.getPhone());
        userInfo.put("avataurl",user.getAvataurl());
        cacheService.setHashMap(user.getUserId(),userInfo);

        return UserLoginVO.builder()
                .userId(user.getUserId())
                .openid(user.getOpenid())
                .token(token)
                .build();
    }

    /**
     * 用户注册
     * @param logupRequest
     * @return
     */
    public UserLogupVO logup(LogupRequest logupRequest) {
        System.out.println("用户注册："+logupRequest.getNickName());
        User user = userMapper.getInfo(logupRequest.getOpenid());
        if(user != null){
            System.out.println("用户已经注册，不能重复注册："+logupRequest.getNickName());
        }else{
            String passwordSHA = AlgorithmUtil.getSHACal(logupRequest.getPassword());
            userMapper.addUser(User.builder().openid(logupRequest.getOpenid())
                    .avataurl(logupRequest.getAvatarUrl())
                    .password(passwordSHA)
                    .nickName(logupRequest.getNickName())
                    .phone(logupRequest.getPhone()).build());
        }
        user = userMapper.getInfo(logupRequest.getOpenid());

        return UserLogupVO.builder().userId(user.getUserId())
                .success(1).build();
    }
}
