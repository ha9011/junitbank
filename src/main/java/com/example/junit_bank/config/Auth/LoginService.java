package com.example.junit_bank.config.Auth;

import com.example.junit_bank.domain.user.User;
import com.example.junit_bank.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 로그인시 세션만들어주는 놈
    // 시큐리티로 로그인될때, 시큐리티가 이 loadUserByUsername()를 실행해서 username을 통해 아이디 유무 체크!!
    // 없으면, 에러
    // 있으면, 정상적으로 시큐리티 킨텍스트 내부 세션에 로그인된 세션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userPs = userRepository.findByUsername(username).orElseThrow(
                () -> new InternalAuthenticationServiceException("인증실패") // 나중에 테스트할때 설명해드림
        );


        return new LoginUser(userPs); // 이 객체가 세션이 만들어진다.
    }
}
