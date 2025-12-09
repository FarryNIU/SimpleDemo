package com.bamboo.firstdemo.controller.bean;

import lombok.Data;

@Data
public class BookRequest {
    private String openid;
    private String userId;
    private String lessonId;
}
