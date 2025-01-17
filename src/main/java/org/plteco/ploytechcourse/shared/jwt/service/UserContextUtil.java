package org.plteco.ploytechcourse.shared.jwt.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.shared.jwt.dto.CustomUserDetails;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserContextUtil {

    private final UserRepository userRepository;

    // 이메일을 얻는 메서드
    public String getEmail() {
        CustomUserDetails customUserDetails = getCurrentUserDetails();
        if (customUserDetails != null) {
            return customUserDetails.getEmail();
        }
        throw new RuntimeException("User not authenticated");
    }

    public RoleEnum getRole() {
        GrantedAuthority authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No authorities found"));

        // 권한에서 'ROLE_' 접두어를 제거한 후 RoleEnum으로 변환
        return RoleEnum.valueOf(authority.getAuthority());
    }




    // UID를 얻는 메서드
    public String getUid() {
        CustomUserDetails customUserDetails = getCurrentUserDetails();
        if (customUserDetails != null) {
            return customUserDetails.getUid();
        }
        throw new RuntimeException("User not authenticated");
    }

    // 현재 인증된 사용자 ID를 얻는 메서드
    public Long getId() {
        return userRepository.findByEmail(getEmail()).getId();
    }

    // 현재 인증된 사용자 정보(CustomUserDetails)를 얻는 메서드
    private CustomUserDetails getCurrentUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        }
        return null;
    }
}
