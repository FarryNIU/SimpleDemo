package com.bamboo.firstdemo.interceptor;

import com.bamboo.firstdemo.util.RequestContext;
import com.bamboo.firstdemo.util.RequestIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestIdInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 生成 ID
        long requestId = RequestIdUtil.getId();
        // 2. 存入线程上下文
        RequestContext.setRequestId(requestId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求结束后，清理当前线程的数据
        RequestContext.clear();
    }
}