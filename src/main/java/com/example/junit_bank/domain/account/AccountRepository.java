package com.example.junit_bank.domain.account;

import com.example.junit_bank.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<User, Long> {
    
}
