package com.bamboo.firstdemo.test;

import com.bamboo.firstdemo.bean.User;
import com.bamboo.firstdemo.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author FarryNiu 2025/11/8
 */
public class TestMysql {
    // TODO: ASDF
    @Autowired(required = false)
    private UserMapper userMapper;
    private void test(){
    }
    public static void main(String[] args){
        TestMysql testMysql = new TestMysql();
        testMysql.test();
    }
}
