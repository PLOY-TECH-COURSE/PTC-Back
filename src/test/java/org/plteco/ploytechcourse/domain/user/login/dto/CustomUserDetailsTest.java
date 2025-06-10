package org.plteco.ploytechcourse.domain.user.login.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    private User testUser;
    private CustomUserDetails customUserDetails;
    private final String testEmail = "test@example.com";
    private final String testUid = "test-uid-123";
    private final String testName = "Test User";
    private final String testPassword = "password123";
    private final RoleEnum testRole = RoleEnum.ROLE_USER;

    @BeforeEach
    void setUp() {
        // 테스트용 User 객체 생성
        testUser = User.builder()
                .uid(testUid)
                .name(testName)
                .email(testEmail)
                .password(testPassword)
                .role(testRole)
                .grade(1L)
                .classNumber(1L)
                .number(1L)
                .build();

        // CustomUserDetails 객체 생성
        customUserDetails = new CustomUserDetails(testUser);
    }

    @Test
    @DisplayName("사용자 권한을 정확히 반환한다")
    void getAuthorities_ReturnsCorrectAuthorities() {
        // when
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();

        // then
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals(testRole.name())));
    }

    @Test
    @DisplayName("사용자 비밀번호를 정확히 반환한다")
    void getPassword_ReturnsCorrectPassword() {
        // when
        String password = customUserDetails.getPassword();

        // then
        assertEquals(testPassword, password);
    }

    @Test
    @DisplayName("사용자 이메일을 정확히 반환한다")
    void getEmail_ReturnsCorrectEmail() {
        // when
        String email = customUserDetails.getEmail();

        // then
        assertEquals(testEmail, email);
    }

    @Test
    @DisplayName("사용자 UID를 정확히 반환한다")
    void getUid_ReturnsCorrectUid() {
        // when
        String uid = customUserDetails.getUid();

        // then
        assertEquals(testUid, uid);
    }

    @Test
    @DisplayName("사용자 이름을 정확히 반환한다")
    void getUsername_ReturnsCorrectUsername() {
        // when
        String username = customUserDetails.getUsername();

        // then
        assertEquals(testName, username);
    }

    @Test
    @DisplayName("계정 만료 여부를 항상 true로 반환한다")
    void isAccountNonExpired_AlwaysReturnsTrue() {
        // when
        boolean isAccountNonExpired = customUserDetails.isAccountNonExpired();

        // then
        assertTrue(isAccountNonExpired);
    }

    @Test
    @DisplayName("계정 잠금 여부를 항상 true로 반환한다")
    void isAccountNonLocked_AlwaysReturnsTrue() {
        // when
        boolean isAccountNonLocked = customUserDetails.isAccountNonLocked();

        // then
        assertTrue(isAccountNonLocked);
    }

    @Test
    @DisplayName("자격 증명 만료 여부를 항상 true로 반환한다")
    void isCredentialsNonExpired_AlwaysReturnsTrue() {
        // when
        boolean isCredentialsNonExpired = customUserDetails.isCredentialsNonExpired();

        // then
        assertTrue(isCredentialsNonExpired);
    }

    @Test
    @DisplayName("계정 활성화 여부를 항상 true로 반환한다")
    void isEnabled_AlwaysReturnsTrue() {
        // when
        boolean isEnabled = customUserDetails.isEnabled();

        // then
        assertTrue(isEnabled);
    }
}