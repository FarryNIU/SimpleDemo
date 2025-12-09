package com.bamboo.firstdemo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "bamboo.mq.bussiness")
public class MqProperties {
    private String queue;
    private String exchange;
    private String routingkey;
}
