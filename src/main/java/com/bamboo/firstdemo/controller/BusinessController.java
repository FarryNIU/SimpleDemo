package com.bamboo.firstdemo.controller;

import com.bamboo.firstdemo.annotation.RateLimit;
import com.bamboo.firstdemo.controller.bean.BookRequest;
import com.bamboo.firstdemo.controller.vo.BookConfirmVO;
import com.bamboo.firstdemo.controller.vo.UserLoginVO;
import com.bamboo.firstdemo.mq.MqProducer;
import com.bamboo.firstdemo.mq.MqSender;
import com.bamboo.firstdemo.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/business")
@Slf4j
public class BusinessController {
    @Autowired
    BusinessService businessService;

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
        log.info("Controller被调用:book");
        return businessService.book(bookRequest);
    }
}
