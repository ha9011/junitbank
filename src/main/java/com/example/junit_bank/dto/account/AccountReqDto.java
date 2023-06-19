package com.example.junit_bank.dto.account;

import com.example.junit_bank.domain.account.Account;
import com.example.junit_bank.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AccountReqDto {
        @NotNull
        @Digits(integer = 4, fraction = 4) // 최소4, 최대4자
        private Long number;

        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long password;

        public Account toEntity(User user){
            return Account.builder()
                    .number(number)
                    .password(password)
                    .balance(1000L)
                    .user(user)
                    .build();
        }

    }

