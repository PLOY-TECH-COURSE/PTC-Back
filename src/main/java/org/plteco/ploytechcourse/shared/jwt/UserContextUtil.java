package org.plteco.ploytechcourse.shared.jwt;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.login.dto.CustomUserDetails;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 현재 인증된 사용자의 정보를 가져오는 유틸리티 클래스입니다.
 *
 * 이 클래스는 `SecurityContextHolder`에서 인증된 사용자 정보를 추출하여,
 * 이메일, UID, 역할(RoleEnum) 등 사용자의 정보를 쉽게 얻을 수 있도록 합니다.
 */
@Component
@RequiredArgsConstructor
public class UserContextUtil {

    private final UserRepository userRepository;

    /**
     * 현재 인증된 사용자의 이메일을 반환합니다.
     *
     * @return 사용자의 이메일
     * @throws RuntimeException 사용자가 인증되지 않은 경우
     */
    public String getEmail() {
        CustomUserDetails customUserDetails = getCurrentUserDetails();
        if (customUserDetails != null) {
            return customUserDetails.getEmail();
        }
        throw new RuntimeException("User not authenticated");
    }

    /**
     * 현재 인증된 사용자의 역할(RoleEnum)을 반환합니다.
     *
     * @return 사용자의 역할
     * @throws IllegalStateException 권한이 없는 경우
     */
    public RoleEnum getRole() {
        GrantedAuthority authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No authorities found"));

        // 권한에서 'ROLE_' 접두어를 제거한 후 RoleEnum으로 변환
        return RoleEnum.valueOf(authority.getAuthority());
    }

    /**
     * 현재 인증된 사용자의 UID를 반환합니다.
     *
     * @return 사용자의 UID
     * @throws RuntimeException 사용자가 인증되지 않은 경우
     */
    public String getUid() {
        CustomUserDetails customUserDetails = getCurrentUserDetails();
        if (customUserDetails != null) {
            return customUserDetails.getUid();
        }
        throw new RuntimeException("User not authenticated");
    }

    /**
     * 현재 인증된 사용자의 ID를 반환합니다.
     *
     * @return 사용자의 ID
     */
    public Long getId() {
        return userRepository.findByEmail(getEmail()).getId();
    }

    /**
     * 현재 인증된 사용자 정보를 반환합니다.
     *
     * @return 현재 인증된 사용자의 `CustomUserDetails`
     */
    private CustomUserDetails getCurrentUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        }
        return null;
    }
}
