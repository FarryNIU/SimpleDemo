package com.bamboo.firstdemo.dao;

import com.bamboo.firstdemo.bean.User;
import com.bamboo.firstdemo.dao.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataService {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Cacheable(value = "users", key = "#openid", unless = "#result == null")
    public User getUser(String openid){
        log.info("userMapper 查询数据库");
        return userMapper.getInfo(openid);
    }

    public String insertUser(String openid, String avatarUrl, String passwordSHA, String nickName, String phone){
        log.info("userMapper 插入数据库");
        userMapper.addUser(User.builder().openid(openid)
                .avataurl(avatarUrl)
                .password(passwordSHA)
                .nickName(nickName)
                .phone(phone).build());
        return openid;
    }
}
