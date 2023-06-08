package com.example.junit_bank.domain.transaction;

import com.example.junit_bank.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<User, Long> {
    
}
