package com.example.junit_bank.service;

import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserEnum;
import com.example.junit_bank.domain.user.UserRepository;
import com.example.junit_bank.handler.ex.CustomApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    @Transactional // 트랜잭션이 메서드 시작할 때, 시작되고 종료될 때 함께 종료
    public JoinRespDto 회원가입(JoinReqDto joinReqDto){
        // 1. 동일 유저 네임 존재 검사
        Optional<User> userOp = userRepository.findByUsername(joinReqDto.getUsername());
        if(userOp.isPresent()){
            // 유저네임 중복
            throw new CustomApiException("동일한 username이 존재합니다");
        }

        // 2. 패스워드 인코딩 + 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity());

        // 3. DTO 응답
        return new JoinRespDto(userPS);
    }

    public static class JoinRespDto{
        private Long id;
        private String username;
        private String fullname;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.fullname = user.getFullname();
        }
    }
    @Getter
    @Setter
    public static class JoinReqDto{
        private String username;
        private String password;
        private String email;
        private String fullname;

        public User toEntity(){
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();


        }
    }
}
