package com.example.junit_bank.config.dummy;

import com.example.junit_bank.domain.account.Account;
import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class DummyObject {
    // save용
    protected static User newUser(String username, String fullname){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encPw = bCryptPasswordEncoder.encode("1234");

        User ssar = User.builder()
                .username(username)
                .password(encPw)
                .email(username+ "@nate.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .build();
        return ssar;
    }
    // 비교용
    protected User newMockUser(Long id, String username, String fullname){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encPw = bCryptPasswordEncoder.encode("1234");

        User ssar = User.builder()
                .id(id)
                .username(username)
                .password(encPw)
                .email(username+ "@nate.com")
                .fullname(fullname)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(UserEnum.CUSTOMER)
                .build();
        return ssar;
    };

    protected Account newMockAccount(Long id, Long number, User user, Long balance) {
        return Account.builder()
                .id(id)
                .number(number)
                .password(1234L)
                .balance(balance)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
