package org.plteco.ploytechcourse.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.application.dto.ApplyApplicationDto;
import org.plteco.ploytechcourse.domain.application.model.TechCourseForm;
import org.plteco.ploytechcourse.domain.application.repository.ApplicationRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ApplyApplicationServiceImpl implements ApplyApplicationService {
    private final UserContextUtil userContextUtil;
    private final ApplicationRepository applicationRepository;
    private final int INTRODUCTION_MAX=500;
    private final int RESOLUTION_MAX=500;

    @Override
    public TechCourseForm apply(ApplyApplicationDto applyApplicationDto) {
        return applicationRepository.save(TechCourseForm.builder()
                        .user(userContextUtil.getCurrentUser())
                        .introduction(applyApplicationDto.getIntroduction())
                        .resolution(applyApplicationDto.getResolution()).build());
    }

    @Override
    public boolean isValidDuplicationStudent(Long userId) {
        return !applicationRepository.existsByUserId(userId);
    }


}
