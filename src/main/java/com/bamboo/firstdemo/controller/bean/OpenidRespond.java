package com.bamboo.firstdemo.controller.bean;

/**
 * 微信小程序前端发起调用，转发请求后得到的openid
 */

public class OpenidRespond {
    private String openid;

    public OpenidRespond(String openid) {
        this.openid = openid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
