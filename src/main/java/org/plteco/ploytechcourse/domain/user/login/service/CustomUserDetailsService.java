package org.plteco.ploytechcourse.domain.user.login.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.login.dto.CustomUserDetails;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CustomUserDetailsService 클래스는 Spring Security의 UserDetailsService를 구현하여,
 * 사용자 인증을 위한 UserDetails를 반환합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 이메일을 기반으로 사용자를 조회하고, 해당 사용자에 대한 UserDetails 객체를 반환합니다.
     *
     * @param username 사용자의 이메일
     * @return UserDetails 객체 (CustomUserDetails)
     * @throws UsernameNotFoundException 사용자가 존재하지 않는 경우 예외를 발생시킴
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 이메일을 기반으로 사용자 데이터를 찾음
        User userData = userRepository.findByEmail(username);

        // 사용자가 존재하면 CustomUserDetails를 반환
        if (userData != null) {
            return new CustomUserDetails(userData);
        }

        // 사용자가 없으면 null 반환, Spring Security에서는 예외를 던지는 것이 일반적임
        return null;
    }
}
