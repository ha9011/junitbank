package com.example.junit_bank.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.junit_bank.config.Auth.LoginUser;
import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JwtProcess {
    private final Logger log = LoggerFactory.getLogger(getClass());

    // 토큰 생성
    public static String create(LoginUser loginUser) {
        String jwtToken = JWT.create()
                .withSubject("bank")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVo.EXPIRATION_TIME))
                .withClaim("id", loginUser.getUser().getId())
                .withClaim("role", loginUser.getUser().getRole().name()) // enum이라 스트링으로해줘야함
                .sign(Algorithm.HMAC256(JwtVo.secret));

        return JwtVo.TOKOEN_PREFIX+jwtToken;
    }

    // 토큰 검증
    // return되는 LoginUser객체를 강제로 시큐리티 세션에 직접 주입
    public static LoginUser verify(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(JwtVo.secret)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        User user = User.builder()
                .id(id)
                .role(UserEnum.valueOf(role))
                .build();
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }


}
