package org.plteco.ploytechcourse.domain.user.login.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.plteco.ploytechcourse.domain.user.login.dto.CustomUserDetails;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User testUser;
    private final String testEmail = "test@example.com";
    private final String testUid = "test-uid-123";
    private final String testName = "Test User";
    private final String testPassword = "password123";

    @BeforeEach
    void setUp() {
        // 테스트용 User 객체 생성
        testUser = User.builder()
                .uid(testUid)
                .name(testName)
                .email(testEmail)
                .password(testPassword)
                .role(RoleEnum.ROLE_USER)
                .grade(1L)
                .classNumber(1L)
                .number(1L)
                .build();
    }

    @Test
    @DisplayName("이메일로 사용자를 찾아 UserDetails를 반환한다")
    void loadUserByUsername_WithValidEmail_ReturnsUserDetails() {
        // given
        when(userRepository.findByEmail(testEmail)).thenReturn(testUser);

        // when
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(testEmail);

        // then
        assertNotNull(userDetails);
        assertTrue(userDetails instanceof CustomUserDetails);
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        assertEquals(testEmail, customUserDetails.getEmail());
        assertEquals(testUid, customUserDetails.getUid());
        assertEquals(testName, customUserDetails.getUsername());
        assertEquals(testPassword, customUserDetails.getPassword());
        assertEquals(1, customUserDetails.getAuthorities().size());
        assertTrue(customUserDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(RoleEnum.ROLE_USER.name())));

        // verify
        verify(userRepository, times(1)).findByEmail(testEmail);
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 조회시 UsernameNotFoundException을 발생시킨다")
    void loadUserByUsername_WithInvalidEmail_ThrowsUsernameNotFoundException() {
        // given
        String nonExistentEmail = "nonexistent@example.com";
        when(userRepository.findByEmail(nonExistentEmail)).thenReturn(null);

        // when & then
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(nonExistentEmail);
        });

        assertEquals(nonExistentEmail, exception.getMessage());

        // verify
        verify(userRepository, times(1)).findByEmail(nonExistentEmail);
    }
}