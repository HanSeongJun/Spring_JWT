package com.cos.jwt.Config.jwt;

import com.cos.jwt.Config.auth.PrincipalDetails;
import com.cos.jwt.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;

// 스프링 시큐리티에 UsernamePasswordAuthenticationFilter가 있음
// /login 요청에서 username, password 전송하면(POST)
// UsernamePasswordAuthenticationFilter가 작동한다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager; // SecurityConfig에 IoC 등록해둔거 호출.

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중");

        // 1. username, password를 받아서
        User user;

        try {
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while( (input = br.readLine()) != null ) {
//                System.out.println(input);
//            }
            ObjectMapper om = new ObjectMapper();
            user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            // 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // 2. 정상인지 로그인 시도 해보기. authenticationManager로 로그인 시도!!
            // PrincipalDetailsService 호출 됨. loadUserByUsername() 함수 실행된 후 정상이면 authentication이 리턴됨.
            // DB에 있는 username과 password가 일치한다.
            Authentication auth = authenticationManager.authenticate(authenticationToken);

            // 3. PrincipalDetails를 세션에 담고(권한 관리를 위해서)
            PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
            System.out.println(principalDetails.getUser().getUsername());
            // Authentication에 객체가 session 영역에 저장해야하고 그 방법이 return 해주면 됨.
            // return 의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는거.
            // 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 단지 권한 처리 때문에 session에 넣어줌.


            // 4. JWT 토큰을 만들어서 응답해주면 됨.
            System.out.println("==================================");
            return auth;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("==================================");
        return null;
    }

    // atteptAuthentication실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행된다.
    // JWT 토큰을 만들어서 request요청한 사용자에게 JWT 토큰을 response 해주면 된다.

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨 : 인증이 완료되었다는 뜻.");
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
