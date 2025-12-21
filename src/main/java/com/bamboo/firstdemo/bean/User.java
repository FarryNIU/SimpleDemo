package com.bamboo.firstdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FarryNiu 2025/11/6
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String openid;
    private String nickName;
    private String avataUrl;
    private String phone;
    private String password;
}
