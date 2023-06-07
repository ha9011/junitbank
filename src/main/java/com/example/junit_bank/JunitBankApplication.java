package com.example.junit_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JunitBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(JunitBankApplication.class, args);
    }

}
