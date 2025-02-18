package org.plteco.ploytechcourse.domain.user.profile.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateProfileImpl implements UpdateProfile {

    private final UserContextUtil userContextUtil;
    @Override
    public void updateProfile(String profile) {
        userContextUtil.getCurrentUser().updateProfile(profile);
    }
}
