package com.example.junit_bank.dto.user;

import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserReqDto {



    @Getter
    @Setter
    public static class LoginReqDto{
        private String username;
        private String password;
    }
    @Getter
    @Setter
    @ToString
    public static class JoinReqDto{
        @NotEmpty // null이거나 공백힐수 없다
        @Pattern(regexp = "^[0-9a-zA-Z]{2,20}$", message = "영문/숫자 2~20내로 입력해 주세요")
        private String username;
        @NotEmpty
        @Size(min=4, max = 20)
        private String password;
        @NotEmpty
        @Pattern(regexp = "^[0-9a-zA-Z]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", message = " 이메일 형식으로 입력해주세요")
        private String email;
        @NotEmpty
        @Pattern(regexp = "^[ㄱ-힣a-zA-Z]{2,20}$", message = "한글/영문 1~20내로 입력해 주세요")
        private String fullname;

        public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder){
            return User.builder()
                    .username(username)
                    .password(bCryptPasswordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();


        }
    }
}
