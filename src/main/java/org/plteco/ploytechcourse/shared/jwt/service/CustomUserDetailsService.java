package org.plteco.ploytechcourse.shared.jwt.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.shared.jwt.dto.CustomUserDetails;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userData = userRepository.findByEmail(username);
        if (userData != null) {
            return new CustomUserDetails(userData);
        }
        return null;
    }
}
