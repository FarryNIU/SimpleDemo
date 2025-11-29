package com.bamboo.firstdemo.bean;

/**
 * @author FarryNiu 2025/11/6
 */
public class User {
    private String userId;
    private String openid;
    private String nickName;
    private String avataurl;
    private String phone;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvataurl() {
        return avataurl;
    }

    public void setAvataurl(String avataurl) {
        this.avataurl = avataurl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
