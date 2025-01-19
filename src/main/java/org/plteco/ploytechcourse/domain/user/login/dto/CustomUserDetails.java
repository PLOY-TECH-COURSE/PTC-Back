package org.plteco.ploytechcourse.domain.user.login.dto;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * CustomUserDetails 클래스는 Spring Security의 UserDetails 인터페이스를 구현하여,
 * 사용자 인증 및 권한 부여에 필요한 정보를 제공합니다.
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    /**
     * 사용자에게 부여된 권한을 반환합니다.
     * 권한은 User 객체의 role 정보를 기반으로 반환됩니다.
     *
     * @return 사용자 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자 역할(role)을 기반으로 GrantedAuthority를 생성하여 반환
        return Collections.singletonList((GrantedAuthority) () -> user.getRole().name());
    }

    /**
     * 사용자의 비밀번호를 반환합니다.
     *
     * @return 사용자 비밀번호
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 사용자의 이메일을 반환합니다.
     *
     * @return 사용자 이메일
     */
    public String getEmail() {
        return user.getEmail();
    }

    /**
     * 사용자의 UID를 반환합니다.
     *
     * @return 사용자 UID
     */
    public String getUid() {
        return user.getUid();
    }

    /**
     * 사용자 이름을 반환합니다.
     * Spring Security에서는 사용자 이름을 통해 인증을 처리합니다.
     *
     * @return 사용자 이름
     */
    @Override
    public String getUsername() {
        return user.getName();
    }

    /**
     * 계정 만료 여부를 반환합니다.
     * 여기서는 항상 true로 반환하여 계정 만료가 없음을 의미합니다.
     *
     * @return 계정 만료 여부
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠금 여부를 반환합니다.
     * 여기서는 항상 true로 반환하여 계정이 잠금되지 않았음을 의미합니다.
     *
     * @return 계정 잠금 여부
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 자격 증명 만료 여부를 반환합니다.
     * 여기서는 항상 true로 반환하여 자격 증명이 만료되지 않았음을 의미합니다.
     *
     * @return 자격 증명 만료 여부
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정 활성화 여부를 반환합니다.
     * 여기서는 항상 true로 반환하여 계정이 활성화되었음을 의미합니다.
     *
     * @return 계정 활성화 여부
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
