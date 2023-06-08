package com.example.junit_bank.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // select * from User where username = {username};
    Optional<User> findByUsername(String username); // Jpa name query - 공식문서 'query method'&'query creation' 참고

    // save는 이미 만들어져있음
}
