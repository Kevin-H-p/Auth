package com.kevin.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author huyanghui
 * @date 2021-12-21 15:43
 */
@Configuration
public class PemProperties {
    public static String SECRET;
    public static Long EXPIRE;

    @Value("${token.secret}")
    public void setSecret(String secret) {
        SECRET = secret;
    }

    @Value("${token.expire}")
    public void setExpire(Long expire) {
        EXPIRE = expire;
    }
}
