package com.example.junit_bank.config;

import com.example.junit_bank.config.Auth.JwtAuthenticationFilter;
import com.example.junit_bank.config.Auth.JwtAuthorizationFilter;
import com.example.junit_bank.domain.user.UserEnum;
import com.example.junit_bank.dto.ResponseDto;
import com.example.junit_bank.util.CustomResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


//@Slf4j junit 테스트 할때 좀 문제생김
@Configuration //설정파일로 빈등록
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());


    // @Configuration 에 붙이 있는 Bean만 작동
    @Bean // IOC 컨테이너에 BCryptPasswordEncoder() 객체가 등록
    public BCryptPasswordEncoder passwordEncoder(){
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    // JWT 필터 등록 필요
    // 필터를 만들었고 filterChain에서 필터 등록이 필요
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class); // 인증매니저
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
            super.configure(builder);
        }
    }

    //JWT 서버를 만들 예정!! session사용안함
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        log.debug("디버그 : filterChain 빈 등록됨");
        http.headers().frameOptions().disable(); // iframe 허용안함
        http.csrf().disable(); // enable 이면 post맨 작동 안함
        http.cors().configurationSource(configurationSource()); //

        // SessionId를 서버쪽에서 관리안하겠다는 뜻!!
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 리액트, 앱 에서 받겠다 // form형태 로그인 안스겠다.
        http.formLogin().disable();
        // httpbasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행하는 걸 막는다
        http.httpBasic().disable();
        // Exception 가로채기

        // authenticationEntryPoint 인증관련 에러일 경우
        http.exceptionHandling().authenticationEntryPoint((request, response, authException)->{
            CustomResponseUtil.fail(response, "로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED);
        });

        // 권한 실패
        http.exceptionHandling().accessDeniedHandler((req, res, e) -> {
            CustomResponseUtil.fail(res, "권한이 없습니다", HttpStatus.FORBIDDEN);
        });

        //필터 적용
        http.apply(new CustomSecurityFilterManager());

        http.authorizeHttpRequests()
                .antMatchers("/api/s/**").authenticated()
                .antMatchers("/api/admin/**").hasRole(""+ UserEnum.ADMIN) // 최근 공식문서에서는 ROLE_ 안붙여도됨
                .anyRequest().permitAll();

        return http.build();
    }

    public CorsConfigurationSource configurationSource(){

        log.debug("디버그 : configurationSource cors 설정이  filterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // put post get delete ( JS 요청 허용 )
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 주소에 위에 설정을 적용시키겠다.

        return source;
    }
}
