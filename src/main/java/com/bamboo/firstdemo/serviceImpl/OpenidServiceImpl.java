package com.bamboo.firstdemo.serviceImpl;

import com.bamboo.firstdemo.config.GlobalConfig;
import com.bamboo.firstdemo.controller.bean.OpenidRespond;
import com.bamboo.firstdemo.service.OpenidService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author FarryNiu 2022/10/30
 */
@Service
public class OpenidServiceImpl implements OpenidService {
    @Override
    public OpenidRespond getOpenid(String code) {
        Logger log = LogManager.getLogger();
        RestTemplate re = new RestTemplate();
        String appId = GlobalConfig.APP_ID;
        String secret = GlobalConfig.APP_SECRET;
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",appId,secret,code);
        String res = re.getForObject(url, String.class);
        log.info("返回：{}",res);
        String openId = res.split(",")[1].split(":")[1].substring(1,29);
        System.out.println(openId);
        return new OpenidRespond(openId);
    }
}
