package com.ncusoft.app_platform.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * @author monkJay
 */
public class JwtUtil {

    /**
     * 生成token，将用户id存入数据
     * @param pwd 账号密码
     * @param uid 账号ID
     * @return 生成的token字符串
     */
    public static String generateToken(String pwd, Long uid){
        // MD5加密的密码和当前时间戳作为数字签证
        String secret = GetString.getMd5(pwd) + System.currentTimeMillis();
        return JWT.create()
                .withAudience(String.valueOf(uid))
                .sign(Algorithm.HMAC256(secret));
    }
}