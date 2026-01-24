package com.bamboo.firstdemo.interceptor;

import com.bamboo.firstdemo.properties.JwtProperties;
import com.bamboo.firstdemo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    @SneakyThrows()
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String authentication = request.getHeader(jwtProperties.getUserTokenName());

        try {
            // 模拟开发新功能：本周需要上线3行代码
            log.info("jwt校验:{}", authentication);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), authentication);
            String userId = claims.get("userId", String.class);
            String openid = claims.get("openid", String.class);

            log.info("当前小程序用户ID：{}, openid: {}", userId, openid);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("token已过期");
            response.getWriter().write("{\"code\":401,\"message\":\"Token expire\"}");
            return false;
        } catch (Exception e){
            log.info("token不合法");
            response.getWriter().write("{\"code\":401,\"message\":\"Token invalid\"}");
            return false;
        }
    }
}
// testA1
// testA2
