package com.example.junit_bank.dto.account;

import com.example.junit_bank.domain.account.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRespDto {
    public AccountRespDto(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.balance = account.getBalance();
    }

    private Long id;
    private Long number;
    private Long balance;
}
