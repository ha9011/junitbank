package com.example.junit_bank.service;

import com.example.junit_bank.config.SecurityConfig;
import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserEnum;
import com.example.junit_bank.domain.user.UserRepository;
import com.example.junit_bank.dto.user.UserReqDto;
import com.example.junit_bank.dto.user.UserRespDto;
import com.example.junit_bank.dto.user.UserRespDto.JoinRespDto;
import com.example.junit_bank.handler.ex.CustomApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.example.junit_bank.dto.user.UserReqDto.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    @Transactional // 트랜잭션이 메서드 시작할 때, 시작되고 종료될 때 함께 종료
    public JoinRespDto 회원가입(JoinReqDto joinReqDto){
        // 1. 동일 유저 네임 존재 검사
        Optional<User> userOp;
        userOp = userRepository.findByUsername(joinReqDto.getUsername());
        if(userOp.isPresent()){
            // 유저네임 중복
            throw new CustomApiException("동일한 username이 존재합니다");
        }

        // 2. 패스워드 인코딩 + 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity(securityConfig.passwordEncoder()));

        // 3. DTO 응답
        return new JoinRespDto(userPS);
    }



}
