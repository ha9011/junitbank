package com.example.junit_bank.config;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@AutoConfigureMockMvc // Mock(가짜) 환경에 MockMvc가 등록됨
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // 가짜환경에서 테스트
public class SecurityConfigTest {

    // 가짜 환경에 등록된 MockMvc를 DI함
    @Autowired
    private MockMvc mvc;

    // 서버는 일관성 있게 에러가 리턴되어야한다.
    // 그렇지 않으면 프론트에서 처리가 어려워진다.
    // 내가 모르는 에러가 프론트한테 날라가지 않게 내가 직접 다 제어하자
    @Test
    public void authentication_test() throws Exception{
        //given
        
        
        //when
        ResultActions perform = mvc.perform(get("/api/s/hello"));
        String responseBody = perform.andReturn().getResponse().getContentAsString();
        int httpStatusCode = perform.andReturn().getResponse().getStatus();
        System.out.println("Test : " +responseBody);
        System.out.println("Test : " +httpStatusCode);


        //then
        assertThat(httpStatusCode).isEqualTo(403);
    }

    @Test
    public void authorization_test() throws Exception{
        //given


        //when
        ResultActions perform = mvc.perform(get("/api/admin/hello"));
        String responseBody = perform.andReturn().getResponse().getContentAsString();
        int httpStatusCode = perform.andReturn().getResponse().getStatus();
        System.out.println("Test : " +responseBody);
        System.out.println("Test : " +httpStatusCode);


        //then
        assertThat(httpStatusCode).isEqualTo(401);
    }
}
