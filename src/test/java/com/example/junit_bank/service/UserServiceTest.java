package com.example.junit_bank.service;

import com.example.junit_bank.config.SecurityConfig;
import com.example.junit_bank.config.dummy.DummyObject;
import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserEnum;
import com.example.junit_bank.domain.user.UserRepository;
import com.example.junit_bank.dto.user.UserReqDto;
import com.example.junit_bank.dto.user.UserReqDto.JoinReqDto;
import com.example.junit_bank.dto.user.UserRespDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.junit_bank.dto.user.UserRespDto.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// spring 관련 bean들이 하나도 없는 환경
// 따라서 가짜환경에 따로 주입을 해줘야함.
@ExtendWith(MockitoExtension.class) // service를 테스트할때 쓴데용
public class UserServiceTest extends DummyObject {

    @InjectMocks
    private UserService userService;

    @Mock // 우리는 service를 테스트 하는거라 실제로 띄우필요없음 따라서 @Mock
    private UserRepository userRepository;
    // 가짜로 메모리에 띄운 후 @InjectMocks 에 기재된 곳에 주입을 함

    @Spy // @mock과 반대로 진짜 걸 띄워서 @InjectMocks에 넣는다
    private SecurityConfig securityConfig;

    @Test
    public void 회원가입_test() throws Exception{
        //given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@nate.com");
        joinReqDto.setFullname("쌀");

        // stub 가정법, 이걸 실행하면 어떤 결과값을 줄거야
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        //when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        User ssar = newMockUser(1L, "ssar", "쌀");
        when(userRepository.save(any())).thenReturn(ssar);

        //when
        JoinRespDto joinRespDto = userService.회원가입(joinReqDto);

        System.out.println("테스트 :: " + joinRespDto);

        //then
        assertThat(joinRespDto.getId()).isEqualTo(1L);
        assertThat(joinRespDto.getUsername()).isEqualTo("ssar");
    }
}
