package com.bamboo.firstdemo.bean;

/**
 * @author FarryNiu 2025/11/6
 */
public class User {
    private String username;
    private String upw;

    public User(String username, String upw) {
        this.username = username;
        this.upw = upw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUpw() {
        return upw;
    }

    public void setUpw(String upw) {
        this.upw = upw;
    }
}
