package com.kevin.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kevin
 * @date 2021-12-20 14:26
 */
@Slf4j
public class TokenUtil {

    public static String token(String... params) {

        String token = "";
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis()   +PemProperties.EXPIRE* 10000);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(PemProperties.SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            token = JWT.create()
                    .withHeader(header)
                    .withSubject(params[0])
                    .withClaim("userName", params[1])
                    .withClaim("accountType", params[2])
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("获取token异常", e);
            return null;
        }
        return token;
    }

    public static DecodedJWT getJwt(String token) {
        /**
         * @desc 验证token，通过返回true
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(PemProperties.SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (Exception e) {
            log.error("验证token异常", e);
            return null;
        }
    }
}
