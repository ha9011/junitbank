package com.example.junit_bank.dto.user;

import com.example.junit_bank.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserRespDto {
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
