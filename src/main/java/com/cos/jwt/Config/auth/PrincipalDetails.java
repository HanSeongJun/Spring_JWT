package com.cos.jwt.Config.auth;

import com.cos.jwt.Model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티에서 사용자의 인증 정보를 담는 역할을 하는 UserDetails 인터페이스의 구현체
// 즉, 스프링 시큐리티가 사용자 정보를 조회할 때 사용하는 Principal 객체
public class PrincipalDetails implements UserDetails {
    // UserDetails 인터페이스를 구현하여, 스프링 시큐리티의 인증 시스템에서 사용자 정보를 관리할 수 있도록 한다.
    // 스프링 시큐리티는 내부적으로 UserDetails 객체를 사용하여 사용자의 인증 상태를 확인한다.

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 사용자의 권한을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoleList().forEach(role -> {
            authorities.add(() -> role);
        });

        return authorities;
    }

    // 현재 로그인한 사용자의 아이디를 반환
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 현재 로그인한 사용자의 암호화된 비밀번호를 반환
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 계정의 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠여 있는지
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
}
