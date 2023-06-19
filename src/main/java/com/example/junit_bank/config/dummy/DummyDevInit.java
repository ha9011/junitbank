package com.example.junit_bank.config.dummy;

import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DummyDevInit extends DummyObject{

    @Profile("dev") // prod 모드에서는 실행안된다
    @Bean
    CommandLineRunner init(UserRepository userRepository){
        return (args) -> {
            // 서버실행시 무조건 실행
            User ssar = userRepository.save(newUser("ssar", "1234"));
        };
    }
}
