package com.bamboo.firstdemo.mq;

import com.bamboo.firstdemo.controller.bean.BookRequest;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class MqProducer {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private RetryTemplate retryTemplate;
    
    /**
     * 带重试的消息发送
     */
    public void sendWithRetry(BookRequest bookRequest, int maxAttempts) throws Exception {
        RetryTemplate retryTemplate = RetryTemplate.builder()
            .maxAttempts(maxAttempts)
            .exponentialBackoff(1000, 2, 10000)
            .retryOn(AmqpException.class)  // 只在AMQP异常时重试
            .build();
        
        retryTemplate.execute(context -> {
            System.out.println("发送尝试: " + (context.getRetryCount() + 1));
            
            CorrelationData correlationData = new CorrelationData(bookRequest.getUserId());
            
            try {
                rabbitTemplate.convertAndSend(
                        "order.exchange",
                    "order.retry",
                    bookRequest,
                    correlationData
                );
                
                // 等待确认
                CorrelationData.Confirm confirm = 
                    correlationData.getFuture().get(2, TimeUnit.SECONDS);
                
                if (!confirm.isAck()) {
                    throw new AmqpException("Broker未确认");
                }
                
                return null;  // 成功，退出重试
                
            } catch (TimeoutException e) {
                throw new AmqpException("确认超时", e);
            }
        });
    }
}