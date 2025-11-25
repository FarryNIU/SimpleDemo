package com.bamboo.firstdemo.controller;

import com.bamboo.firstdemo.controller.bean.Code;
import com.bamboo.firstdemo.controller.bean.OpenidRespond;
import com.bamboo.firstdemo.service.OpenidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FarryNiu 2025/11/25
 */
@RestController
public class OpenidController {
    @Autowired
    OpenidService openidService;

    @PostMapping("/getOpenid")
    @ResponseBody
    public OpenidRespond getOpenid(@RequestBody Code code){
        System.out.println("调用getOpenid "+ code.getCode());
        return openidService.getOpenid(code.getCode());
    }
}
