package com.bamboo.firstdemo.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestIdUtil {
    public static long getId(){
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);

        long id = snowflake.nextId();
        log.info("生成的全局唯一ID: " + id);
        return id;
    }
}
