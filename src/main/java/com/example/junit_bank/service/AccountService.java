package com.example.junit_bank.service;

import com.example.junit_bank.domain.account.Account;
import com.example.junit_bank.domain.account.AccountRepository;
import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserRepository;
import com.example.junit_bank.dto.account.AccountReqDto;
import com.example.junit_bank.dto.account.AccountRespDto;
import com.example.junit_bank.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public AccountRespDto 계좌등록(AccountReqDto accountSaveReqDto, Long userId) {
        // 1. user 검증
        User userPs = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("유저를 찾을 수 없습니다.")
        );

        // 2. 해당 계좌 중복 여부를 체크
        Optional<Account> accountPs = accountRepository.findByNumber(accountSaveReqDto.getNumber());
        if(accountPs.isPresent()){
            throw new CustomApiException("해당 계좌가 이미 존재합니다.");
        }

        // 3. 계좌 등록
        Account newAccount =  accountRepository.save(accountSaveReqDto.toEntity(userPs));


        // 4. Dto 응답
        return new AccountRespDto(newAccount);
    }



}
