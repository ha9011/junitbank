package com.example.junit_bank.config.jwt;


import com.example.junit_bank.config.Auth.LoginUser;
import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserEnum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtProcessTest {
    @Test
    public void create_test() throws Exception {

        //given
        User user = User.builder()
                .id(1L)
                .role(UserEnum.CUSTOMER)
                .build();
        LoginUser loginUser = new LoginUser(user);

        //when

        String jwtToken = JwtProcess.create(loginUser);
        System.out.println(jwtToken);

        //then
        assertTrue(jwtToken.startsWith(JwtVo.TOKOEN_PREFIX));
    }

    @Test
    public void vertify_test() throws Exception {

        //given
        User user = User.builder()
                .id(1L)
                .role(UserEnum.CUSTOMER)
                .build();
        LoginUser loginUser = new LoginUser(user);
        String prefixJwtToken = JwtProcess.create(loginUser);
        System.out.println("prefixJwtToken : " + prefixJwtToken);
        String token = prefixJwtToken.replace(JwtVo.TOKOEN_PREFIX, "");


        //when

        LoginUser verifyToken = JwtProcess.verify(token);
        System.out.println("verifyToken : " + verifyToken.toString());

        //then
        assertThat(verifyToken.getUser().getId()).isEqualTo(user.getId());
        assertThat(verifyToken.getUser().getRole()).isEqualTo(user.getRole());
    }
}
