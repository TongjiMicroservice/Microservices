package com.tongji.microservice.teamsphere.userservice.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Date;
public final class Jwt {
    private static final String secret="secret";
    private static final Algorithm algorithm;
    private static final JWTVerifier verifier;
    static{
        try {
            algorithm = Algorithm.HMAC256(secret);
            verifier = JWT.require(algorithm)
                    // specify a specific claim validations
                    .withIssuer("admin-LLouver")
                    // reusable verifier instance
                    .build();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(int userid, int expireTime){
        if(expireTime <= 1)
            expireTime=1;
        Date expireDate=new Date(System.currentTimeMillis()+expireTime*1000L);
        String token;
        token=JWT.create()
                .withExpiresAt(expireDate)
                .withIssuer("admin-LLouver")
                .withClaim("userid",userid)
                .sign(algorithm);
        return token;
    }

    public static JWTVerifier getVerifier(){
        return verifier;
    }
}
