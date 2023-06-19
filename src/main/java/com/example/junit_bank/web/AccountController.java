package com.example.junit_bank.web;

import com.example.junit_bank.config.Auth.LoginUser;
import com.example.junit_bank.dto.ResponseDto;
import com.example.junit_bank.dto.account.AccountReqDto;
import com.example.junit_bank.dto.account.AccountRespDto;
import com.example.junit_bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/s/account")
    public ResponseEntity<?> saveAccount(
            @RequestBody @Valid AccountReqDto accountReqDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal LoginUser loginUser) {


        AccountRespDto accountSaveRespDto = accountService.계좌등록(accountReqDto, loginUser.getUser().getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "계좌등록성공", accountSaveRespDto), HttpStatus.CREATED);
        }

}
