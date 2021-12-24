package com.kevin.auth;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author huyanghui
 * @date 2021-12-20 17:47
 */
public class JWTHolder {

    private static final ThreadLocal<DecodedJWT> jwtHolder = new ThreadLocal<>();

    public static void setJwt(DecodedJWT decodedJWT) {
        jwtHolder.set(decodedJWT);
    }

    public static DecodedJWT getJwt() {
        return jwtHolder.get();
    }

    public static void remove() {
        jwtHolder.remove();
    }
}

