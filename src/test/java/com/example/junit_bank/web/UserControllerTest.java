package com.example.junit_bank.web;

import com.example.junit_bank.config.dummy.DummyObject;
import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserRepository;
import com.example.junit_bank.dto.user.UserReqDto.JoinReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest(webEnvironment =  WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest extends DummyObject {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        dataSetting();
    }

    private void dataSetting() {
        System.out.println("---data setup--");
        userRepository.save(newUser("ssar", "쌀"));
    }


    @Test
    @Order(1)
    public void join_success_test() throws Exception {
        //given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("love");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("love@nate.com");
        joinReqDto.setFullname("러브");

        String requestBody = om.writeValueAsString(joinReqDto);
        System.out.println("requestBody : " + requestBody);


        //when
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/api/join");
        ResultActions resultActions = mockMvc.perform(post.content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "  + responseBody);


        //then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void join_fail_test() throws Exception {
        //given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@nate.com");
        joinReqDto.setFullname("하하");


        String requestBody = om.writeValueAsString(joinReqDto);
        System.out.println("requestBody : " + requestBody);


        //when
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/api/join");
        ResultActions resultActions = mockMvc.perform(post.content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "  + responseBody);


        //then
        resultActions.andExpect(status().isBadRequest());
    }
}
