package com.example.junit_bank.dto.user;

import com.example.junit_bank.config.Auth.LoginUser;
import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.util.CustomDataUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserRespDto {


    @Getter
    @Setter
    public static class LoginRespDto{
        private Long id;
        private String username;
        private String createdAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.createdAt = CustomDataUtil.toStringFormat(user.getCreatedAt());
        }

    }


    @Getter
    @Setter
    @ToString
    public static class JoinRespDto{
        private Long id;
        private String username;
        private String fullname;
        private String password;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.fullname = user.getFullname();
            this.password = user.getPassword();
        }
    }
}
