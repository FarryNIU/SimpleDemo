package com.bamboo.firstdemo.controller;

import com.bamboo.firstdemo.annotation.RateLimit;
import com.bamboo.firstdemo.controller.bean.BookRequest;
import com.bamboo.firstdemo.controller.vo.BookConfirmVO;
import com.bamboo.firstdemo.controller.vo.UserLoginVO;
import com.bamboo.firstdemo.mq.MqProducer;
import com.bamboo.firstdemo.mq.MqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    MqSender mqSender;

    @Autowired
    MqProducer mqProducer;

    @PostMapping("/book")
    @ResponseBody
    @RateLimit(
            slidingWindowSize = 10,
            slidingMaxRequests = 100,
            tokenCapacity = 1000,
            tokenRefillRate = 100,
            message = "接口请求过于频繁，请稍后重试"
    )
    public BookConfirmVO book(@RequestBody BookRequest bookRequest){
        System.out.println("业务被调用 "+ bookRequest.getUserId());
       // mqSender.initMq();
       // mqSender.sendMessage(bookRequest.getText());
        try {
            mqProducer.sendWithRetry(bookRequest,3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
