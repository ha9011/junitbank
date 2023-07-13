package com.example.junit_bank.config.Auth;

import com.example.junit_bank.config.jwt.JwtProcess;
import com.example.junit_bank.config.jwt.JwtVo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
* 모든 주소에서 동작함 ( 토큰 검증 )
*
* */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // JWT 토큰을 해더에 추가하지 않아도 해당 필터는 통과는 할 수 있지만, 결국 시큐리티단에서 세션값 검증에 실패함
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (isHeaderVerify(request, response)) {
            String token = request.getHeader(JwtVo.HEADER).replace(JwtVo.TOKOEN_PREFIX, "");
            LoginUser loginUser = JwtProcess.verify(token);

            // 임시세션(userDetails 타입 Or username )
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginUser, null, loginUser.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        chain.doFilter(request, response);
        // security는 필터가 다 끝나면 컨트롤러로 넘어감
    }

    private boolean isHeaderVerify(HttpServletRequest req, HttpServletResponse response) {
        String header = req.getHeader(JwtVo.HEADER);
        if (header == null || !header.startsWith(JwtVo.TOKOEN_PREFIX)) {
            return false;
        }
        return true;
    }
}
