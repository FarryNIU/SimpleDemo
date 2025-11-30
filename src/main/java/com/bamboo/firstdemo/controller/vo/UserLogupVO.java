package com.bamboo.firstdemo.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FarryNiu 2025/11/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLogupVO {
    private int success;
    private String userId;
    private String openid;
    private String token;
}
