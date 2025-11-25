package com.bamboo.firstdemo.service;

import com.bamboo.firstdemo.controller.bean.OpenidRespond;

/**
 * @author FarryNiu 2025/11/25
 */
public interface OpenidService {
    OpenidRespond getOpenid(String code);
}
