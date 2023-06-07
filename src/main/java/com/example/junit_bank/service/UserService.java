package com.example.junit_bank.service;

import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    @Transactional // 트랜잭션이 메서드 시작할 때, 시작되고 종료될 때 함께 종료
    public void 회원가입(JoinReqDto joinReqDto){
        // 1. 동일 유저 네임 존재 검사
        Optional<User> userOp = userRepository.findByUsername(joinReqDto.getUsername());
        if(u)

        // 2. 패스워드 인코딩

        // 3. DTO 응답
    }

    @Getter
    @Setter
    public static class JoinReqDto{
        private String username;
        private String password;
        private String email;
        private String fullname;
    }
}
