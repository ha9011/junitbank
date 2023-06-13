package com.example.junit_bank.config.Auth;

import com.example.junit_bank.config.dummy.DummyObject;
import com.example.junit_bank.config.jwt.JwtVo;
import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserRepository;
import com.example.junit_bank.dto.user.UserReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import java.util.Collections;
import java.util.Optional;

import static com.example.junit_bank.dto.user.UserReqDto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc // mock 환경 컨테이너에서 DI을 하려면 필요하다
class JwtAuthenticationFilterTest extends DummyObject {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        System.out.println("::: setup :::");

        userRepository.save(newUser("ssar", "쌀"));
    }

    @BeforeEach
    public void resetUp() {
        System.out.println("::: resetUp :::");

        Optional<User> save = userRepository.findByUsername("ssar");
        userRepository.deleteById(save.get().getId());

    }


    @Test
    public void successfulAuthentication_test() throws  Exception{
        //given
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("ssar");
        loginReqDto.setPassword("1234");
        String reqBody = objectMapper.writeValueAsString(loginReqDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/login")
                .content(reqBody).contentType(MediaType.APPLICATION_JSON));

        String resBody = resultActions.andReturn().getResponse().getContentAsString();
        String jwtToken = resultActions.andReturn().getResponse().getHeader(JwtVo.HEADER);

        System.out.println("resBody : " + resBody);
        System.out.println("resHeader : " + jwtToken);

        // then
        resultActions.andExpect(status().isOk());
        assertNotNull(jwtToken);
        assertTrue(jwtToken.startsWith(JwtVo.TOKOEN_PREFIX));
        resultActions.andExpect(jsonPath("$.data.username").value("ssar"));
        // $가 최상위 json을 의미
    }

    @Test
    public void unsuccessfulAuthentication_test() throws  Exception{
        //given
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("ssar");
        loginReqDto.setPassword("12345"); // 비밀번호 틀리게
        String reqBody = objectMapper.writeValueAsString(loginReqDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/login")
                .content(reqBody).contentType(MediaType.APPLICATION_JSON));

        String resBody = resultActions.andReturn().getResponse().getContentAsString();
        String jwtToken = resultActions.andReturn().getResponse().getHeader(JwtVo.HEADER);

        resultActions.andExpect(status().is4xxClientError()); // 401


    }


}