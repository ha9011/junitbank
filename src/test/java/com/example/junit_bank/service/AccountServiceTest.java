package com.example.junit_bank.service;

import com.example.junit_bank.config.dummy.DummyObject;
import com.example.junit_bank.domain.account.Account;
import com.example.junit_bank.domain.account.AccountRepository;
import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserRepository;
import com.example.junit_bank.dto.account.AccountReqDto;
import com.example.junit_bank.dto.account.AccountRespDto;
import com.example.junit_bank.handler.ex.CustomApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 전체를 메모리를 띄울 필요가 없기 때문에 쓰임
class AccountServiceTest extends DummyObject {

    @InjectMocks // 모든 mock들이 injectMocks로 주입됨
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountRepository accountRepository;

    @Spy // 진짜 객체임 따라서 정말로 쓸수가 있음
    private ObjectMapper om;

    @Test
    public void 계좌등록_test() throws Exception{
        //given

        Long userId = 1L;
        AccountReqDto accountReqDto = new AccountReqDto();
        accountReqDto.setNumber(1111L);
        accountReqDto.setPassword(1234L);

        // stub : 테스트 용도로 하드 코딩한 값을 반환하는 구현체를 의미;
        //stub 1 : 어떤값을 넣었을때(any()), ssar유저가 나올 환경
        User ssar = newMockUser(userId, "ssar", "쌀");
        when(userRepository.findById(any())).thenReturn(Optional.of(ssar));

        //stub 2 :어떤값이든 넣었을때, 계좌번호가 없음
        when(accountRepository.findByNumber(any())).thenReturn(Optional.empty());

        //stub 3
        Account ssarAccount = newMockAccount(1L, 1111L, ssar, 1000L);
        when(accountRepository.save(any())).thenReturn(ssarAccount);
        //when

        AccountRespDto accountSaveDto = accountService.계좌등록(accountReqDto, userId);
        String responseBody = om.writeValueAsString(accountSaveDto);
        System.out.println("테스트 : "  + responseBody);

        //the
        assertThat(accountSaveDto.getNumber()).isEqualTo(ssarAccount.getNumber());
    }

    @Test
    public void 계좌등록_유저없음_test() throws Exception{
        //given
        Long userId = 10L;
        AccountReqDto accountReqDto = new AccountReqDto();
        accountReqDto.setNumber(1111L);
        accountReqDto.setPassword(1234L);

        when(userRepository.findById(any())).thenThrow(new NullPointerException("유저를 찾을 수 없습니다."));
        //when
        try{
            AccountRespDto accountSaveDto = accountService.계좌등록(accountReqDto, userId);
            String resBody = om.writeValueAsString(accountSaveDto);
            System.out.println("test : " + resBody);
        }catch(NullPointerException e) {
            System.out.println("----");
            //then
            assertThrows(Exception.class, ()-> userRepository.findById(any()));
        }


    }

}