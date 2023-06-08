package com.example.junit_bank.handler.ex;

import lombok.Getter;

import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException{

    private Map<String, String> errMap;

    public CustomValidationException(String message, Map<String, String> errMap) {
        super(message);
        this.errMap = errMap;
    }
}
