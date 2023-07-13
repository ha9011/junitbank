package com.example.junit_bank.web;

import com.example.junit_bank.config.dummy.DummyObject;
import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserRepository;
import com.example.junit_bank.dto.account.AccountReqDto;
import com.example.junit_bank.dto.account.AccountRespDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AccountControllerTest extends DummyObject {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        User ssar = userRepository.save(newUser("ssar", "쌀"));
    }

    // jwt token -> 인증필터 -> 시큐리티 세션 ( 해더에 없어도, 세션만 있으면 통과 // **doFilterInternal 메소드 참고

    // setupBefore = TEST_METHOD (메서드 실행전에 수행, 거기다 beforeEach의 setUp보다 먼저 실행됨)
    // 따라서 TestExecutionEvent.TEST_EXECUTION 로 설정해야함 ( saveAcount_test 메서드 실행전에 수행 )
    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION) // 유저이름기준 , 디비에서 username 을 ssar로 조회해서 세션에 담아주는 어노테이션
    @Test
    public void saveAcount_test() throws Exception{
        //given
        AccountReqDto accountReqDto = new AccountReqDto();
        accountReqDto.setNumber(9999L);
        accountReqDto.setPassword(1234L);
        String reqBody = om.writeValueAsString(accountReqDto);
        System.out.println("테스트 : " + reqBody);


        //when

        ResultActions resultActions = mvc.perform(post("/api/s/account").content(reqBody).contentType(MediaType.APPLICATION_JSON));
        String resBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + resBody);


        //then
        //MockMvcRequestBuilders
        //MockMvcResultMatchers
        StatusResultMatchers status = MockMvcResultMatchers.status();
        resultActions.andExpect(status.isCreated());
    }
}