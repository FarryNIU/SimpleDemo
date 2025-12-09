package com.bamboo.firstdemo.serviceImpl;

import com.bamboo.firstdemo.bean.User;
import com.bamboo.firstdemo.controller.bean.LoginRequest;
import com.bamboo.firstdemo.controller.bean.LogupRequest;
import com.bamboo.firstdemo.controller.vo.UserLoginVO;
import com.bamboo.firstdemo.controller.vo.UserLogupVO;
import com.bamboo.firstdemo.dao.DataService;
import com.bamboo.firstdemo.dao.mapper.UserMapper;
import com.bamboo.firstdemo.properties.JwtProperties;
import com.bamboo.firstdemo.service.LoginService;
import com.bamboo.firstdemo.util.AlgorithmUtil;
import com.bamboo.firstdemo.util.CacheService;
import com.bamboo.firstdemo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private DataService dataService;




    /**
     * 用户登录
     * @param loginRequest
     * @return
     */
    @Override
    public UserLoginVO login(LoginRequest loginRequest) {
        System.out.println("用户登录："+loginRequest.getNickName());
        User user = dataService.getUser(loginRequest.getOpenid());
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
        /**
         * 由于通过注解的方式简化，不再需要显示的写出存入缓存的过程了
         */
        /*
        HashMap<String, String> userInfo = new HashMap<>();

        userInfo.put("userId",user.getUserId());
        userInfo.put("phone",user.getPhone());
        userInfo.put("avataurl",user.getAvataurl());
        cacheService.setHashMap("users::"+user.getOpenid(),userInfo);
        */

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
        User user = dataService.getUser(logupRequest.getOpenid());
        if(user != null){
            System.out.println("用户已经注册，不能重复注册："+logupRequest.getNickName());
        }else{
            String passwordSHA = AlgorithmUtil.getSHACal(logupRequest.getPassword());
            dataService.insertUser(logupRequest.getOpenid(),logupRequest.getAvatarUrl(),passwordSHA,logupRequest.getNickName(),logupRequest.getPhone());
        }
        user = dataService.getUser(logupRequest.getOpenid());

        return UserLogupVO.builder().userId(user.getUserId())
                .success(1).build();
    }


}
