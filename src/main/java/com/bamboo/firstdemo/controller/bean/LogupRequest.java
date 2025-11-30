package com.bamboo.firstdemo.controller.bean;

import lombok.Data;

/**
 * @author FarryNiu 2025/11/30
 */
@Data
public class LogupRequest {
    private String userId;
    private String openid;
    private String nickName;
    private String avatarUrl;
    private String password;
    private String phone;

}
