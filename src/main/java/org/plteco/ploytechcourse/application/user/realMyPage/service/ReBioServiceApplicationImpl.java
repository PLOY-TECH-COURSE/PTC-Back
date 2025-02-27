package org.plteco.ploytechcourse.application.user.realMyPage.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.realMyPage.dto.RequestBioDTO;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ReBioServiceApplicationImpl implements ReBioServiceApplication {

    private final UserContextUtil userContextUtil;

    @Override
    public void change(RequestBioDTO requestBioDTO) {
        userContextUtil.getCurrentUser().updateBio(requestBioDTO.getBio());
    }
}
