package com.example.junit_bank.web;

import com.example.junit_bank.dto.ResponseDto;
import com.example.junit_bank.dto.user.UserReqDto;
import com.example.junit_bank.dto.user.UserReqDto.JoinReqDto;
import com.example.junit_bank.dto.user.UserRespDto;
import com.example.junit_bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;

import static com.example.junit_bank.dto.user.UserRespDto.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errMap = new HashMap<>();
            for (FieldError err : bindingResult.getFieldErrors()) {
                errMap.put(err.getField(), err.getDefaultMessage());
            }

            return new ResponseEntity<>(new ResponseDto<>(-1, "유효성검사 실패", errMap), HttpStatus.BAD_REQUEST); // 201 뭔가 만들어질때

        }
        System.out.println("||||join||||| : " + joinReqDto.toString());
        // username==ssar&password&1234....
        // @RequsetBody를 꼭 붙여준다 -> JSON으로 답이옴
        JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinRespDto), HttpStatus.CREATED); // 201 뭔가 만들어질때

    }
}
