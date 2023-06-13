package com.example.junit_bank.config.Auth;

import com.example.junit_bank.config.jwt.JwtProcess;
import com.example.junit_bank.config.jwt.JwtVo;
import com.example.junit_bank.dto.user.UserReqDto;
import com.example.junit_bank.dto.user.UserRespDto;
import com.example.junit_bank.dto.user.UserRespDto.LoginRespDto;
import com.example.junit_bank.util.CustomResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.example.junit_bank.dto.user.UserReqDto.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager ) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
    }


    // post : /login 일 때 // 동작 컨트롤러보다 먼저 실행
    // 근데 난 /api/login으로 하고 싶음
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug(" 디버그 : attemptAuthentication 호출");
        try{
            ObjectMapper om = new ObjectMapper();
            LoginReqDto loginReqDto = om.readValue(request.getInputStream(), LoginReqDto.class);

            // 강제 로그인
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginReqDto.getUsername(), loginReqDto.getPassword()
            );

            // UserDetailService의 loadUserByUserNaem 호출
            // JWT를 쓴다하더라고, 컨트롤러 진입을 하면 시큐리티의 권한체크, 인증체크의 도움을 받을 수 있게 세션을 만든다.
            // 이 세션의 유호기간은 req하고 res하면 끝!!
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        }catch (Exception e ){
            e.printStackTrace();
            // security 에러
            // security config 에 authenticationEntryPoint에서 에러처리됨
            // 필터를 다 통과해야 컨트롤단으로 가기 때문에 @advice에 안걸림
            // 필터를 타고 있는 동안에는 @advice에 안걸림
            //unsuccessfulAuthentication 아래 매소드를 터트림
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    //로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        CustomResponseUtil.fail(response, "로그인 실패", HttpStatus.UNAUTHORIZED);
    }

    // return authentication 잘 작동하면 successfulAuthentication 메서드 호출
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.debug(" 디버그 : successfulAuthentication 호출"); // 로그인(세션)이 됐다는 것
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String jwtToken = JwtProcess.create(loginUser);
        response
                .addHeader(JwtVo.HEADER, jwtToken);

        LoginRespDto loginRespDto = new LoginRespDto(loginUser.getUser());
        CustomResponseUtil.success(response, loginRespDto);
    }
}
