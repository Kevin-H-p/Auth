package com.kevin.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author kevin
 * @date 2021-12-20 14:27
 */
@Slf4j
@Component
public class TokenHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有JWTValidation注释，无则跳过认证
        if (!method.isAnnotationPresent(JWTValidation.class)) {
            return true;
        }

        int type = method.getAnnotation(JWTValidation.class).value()[0];
        String token = request.getHeader("Authorization");

        if (token != null) {
            DecodedJWT jwt = TokenUtil.getJwt(token);
            //判断绑定
            if (jwt != null) {
                JWTHolder.setJwt(jwt);
                Integer accountType = Integer.valueOf(jwt.getClaim("accountType").asString());
                if (accountType <= type) {
                    return true;
                } else {
                    throw new RuntimeException("权限不足,请联系管理员!");
                }
            }
        }
        return false;
    }
}
