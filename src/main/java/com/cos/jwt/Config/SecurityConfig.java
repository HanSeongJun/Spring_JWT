package com.cos.jwt.Config;

import com.cos.jwt.Filter.MyFilter3;
import com.cos.jwt.Config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;

//    @Bean // authenticationManager를 IOC에 등록
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);

        // csrf 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 x
                .formLogin(AbstractHttpConfigurer::disable) // 폼로그인 사용 안함
                .httpBasic(AbstractHttpConfigurer::disable) // httpBasic 사용 안함
                .addFilter(new JwtAuthenticationFilter(authenticationManager)) // 24강
                .addFilter(corsConfig.corsFilter()) // @CrossOrigin(인증 x), 시큐리티 필터에 등록 인증(o) --> 모든 요청을 허용

                /* --------- security 최신 버전에서는 권한 적용시 ROLE_ 쓰지 않음. 즉, USER, ADMIN, MANAGER로 써야함 ---------- */
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/user/**").authenticated() // /user 라는 URL로 들어오면 인증이 필요하다.
                        .requestMatchers("/api/v1/manager/**").hasAnyRole("MANAGER", "ADMIN") // /manager으로 들어오는 MANAGER 인증 또는 ADMIN인증이 필요하다는 뜻이다.
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN") // //admin으로 들어오면 ADMIN권한이 있는 사람만 들어올 수 있음
                        .anyRequest().permitAll() // 그리고 나머지 url은 전부 권한을 허용해준다.
                );

        return http.build();
    }
}
