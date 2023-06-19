package com.example.junit_bank.config.Auth;

import com.example.junit_bank.config.jwt.JwtProcess;
import com.example.junit_bank.config.jwt.JwtVo;
import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc // mock 환경 컨테이너에서 DI을 하려면 필요하다
class JwtAuthorizationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void authorization_success_test() throws Exception{
        //given
        User user = User.builder()
                .id(1L)
                .role(UserEnum.CUSTOMER)
                .build();

        LoginUser loginUser = new LoginUser(user);
        String jwtToken = JwtProcess.create(loginUser);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/s/hello/test").header(JwtVo.HEADER, jwtToken));

        //then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void authorization_fail_test() throws Exception{
        //given
        User user = User.builder()
                .id(1L)
                .role(UserEnum.CUSTOMER)
                .build();

        LoginUser loginUser = new LoginUser(user);
        String jwtToken = JwtProcess.create(loginUser);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/s/hello/test"));

        //then
        resultActions.andExpect(status().isUnauthorized());// 401
    }

    @Test
    public void authorization_admin_fail_test() throws Exception{
        //given
        User user = User.builder()
                .id(1L)
                .role(UserEnum.CUSTOMER)
                .build();

        LoginUser loginUser = new LoginUser(user);
        String jwtToken = JwtProcess.create(loginUser);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/admin/hello/test").header(JwtVo.HEADER, jwtToken));

        //then
        resultActions.andExpect(status().isForbidden());
    }
}