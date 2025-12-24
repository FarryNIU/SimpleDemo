package com.bamboo.firstdemo.serviceImpl;

import com.bamboo.firstdemo.controller.bean.BookRequest;
import com.bamboo.firstdemo.controller.vo.BookConfirmVO;
import com.bamboo.firstdemo.mq.MqProducer;
import com.bamboo.firstdemo.mq.MqSender;
import com.bamboo.firstdemo.service.BusinessService;
import com.bamboo.firstdemo.util.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    MqSender mqSender;

    @Autowired
    MqProducer mqProducer;

    @Override
    public BookConfirmVO book(BookRequest bookRequest) {
        // mqSender.initMq();
        // mqSender.sendMessage(bookRequest.getText());
        try {
            bookRequest.setRequestId(RequestContext.getRequestId());
            mqProducer.sendWithRetry(bookRequest,3);
        } catch (Exception e) {
            log.error("MQ发送异常："+e.getMessage());
        }
        return null;
    }
}
