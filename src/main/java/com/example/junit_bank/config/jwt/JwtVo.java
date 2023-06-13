package com.example.junit_bank.config.jwt;


/*
* SECRET 는 노출 되면 안되고, 환경변수나 클라우드 서비스에 등록 후 꺼내 쓰는게 좋음
* 리플레시 토큰 : accessToken 만료 될 경우, 새로운 토큰을 자동으로 생성해주는 토큰
* */
public interface JwtVo {
    public static final String secret = "메타코딩"; // HS256 (대칭키)
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7 ; // 만료 기간 일주일
    public static final String TOKOEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";


}
