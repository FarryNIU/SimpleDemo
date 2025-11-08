package com.bamboo.firstdemo.dao.mapper;

import com.bamboo.firstdemo.dao.bean.UserDB;

public interface UserMapper {
    /**
     * 登录，获取user信息
     */
    UserDB getInfo(String userId);

    /**
     * 注册，添加一个user信息
     * @param newUser 一个userBean
     */
    void addUser(UserDB newUser);
}
