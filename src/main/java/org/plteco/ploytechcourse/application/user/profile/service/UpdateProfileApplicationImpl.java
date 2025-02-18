package org.plteco.ploytechcourse.application.user.profile.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.profile.dto.RequestProfile;
import org.plteco.ploytechcourse.domain.user.profile.service.UpdateProfile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProfileApplicationImpl implements UpdateProfileApplication {

    private final UpdateProfile updateProfile;

    @Override
    public void updateProfile(RequestProfile profile) {
        updateProfile.updateProfile(profile.getProfile());
    }
}
