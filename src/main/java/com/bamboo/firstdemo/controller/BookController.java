package com.bamboo.firstdemo.controller;

import com.bamboo.firstdemo.controller.bean.BookRequest;
import com.bamboo.firstdemo.controller.bean.LoginRequest;
import com.bamboo.firstdemo.controller.vo.UserLoginVO;
import com.bamboo.firstdemo.mq.MqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookController {
    @Autowired
    MqSender mqSender;

    @PostMapping("/book")
    @ResponseBody
    public UserLoginVO login(@RequestBody BookRequest bookRequest){
        System.out.println("业务被调用 "+ bookRequest.getText());
        mqSender.initMq();
        mqSender.sendMessage(bookRequest.getText());
        return null;
    }
}
