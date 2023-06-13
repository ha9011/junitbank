package com.example.junit_bank.util;

import com.example.junit_bank.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

public class CustomResponseUtil {
    private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    public static void success(HttpServletResponse res, Object dto){
        try{
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(1, "로그인성공", dto);
            String responseBody = om.writeValueAsString(responseDto);

            res.setContentType("application/json; charset=utf-8");
            res.setStatus(200); // 401: 인증, 403: 권한없음
            res.getWriter().println(responseBody);
        }catch (Exception e){
            log.error("서버 파싱 에러");
        }
    }


    public static void fail(HttpServletResponse res, String msg, HttpStatus httpstatus){
        try{
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(-1, msg, null);
            String responseBody = om.writeValueAsString(responseDto);

            res.setContentType("application/json; charset=utf-8");
            res.setStatus(httpstatus.value()); // 401: 인증, 403: 권한없음
            res.getWriter().println(responseBody);
        }catch (Exception e){
            log.error("서버 파싱 에러");
        }
    }

}
