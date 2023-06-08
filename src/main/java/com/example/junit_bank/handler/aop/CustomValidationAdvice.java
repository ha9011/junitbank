package com.example.junit_bank.handler.aop;

import com.example.junit_bank.dto.ResponseDto;
import com.example.junit_bank.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CustomValidationAdvice {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){

    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping(){

    }

    //@before, @after
    @Around("postMapping() || putMapping()") // joinpoint 전 후 제어
    public Object validationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs(); // joinPoint의 매개변수
        for (Object arg : args) {
            if(arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg;
                if(bindingResult.hasErrors()){
                    Map<String, String> errMap = new HashMap<>();
                    for (FieldError err : bindingResult.getFieldErrors()) {
                        errMap.put(err.getField(), err.getDefaultMessage());
                    }
                    System.out.println("--validationAdvice--");
                    throw new CustomValidationException("유효성 검사 실패", errMap);
                }
            }
        }
        System.out.println("--validationAdvice--");
        return proceedingJoinPoint.proceed(); // 아무일 없으면 실행(정상)
    }
}
