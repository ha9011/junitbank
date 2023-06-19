package com.example.junit_bank.domain.account;

import com.example.junit_bank.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // query method
    // select * from account where number = :number

    Optional<Account> findByNumber(Long number);
}
