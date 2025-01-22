package org.plteco.ploytechcourse.domain.application.service;

import org.plteco.ploytechcourse.application.application.dto.ApplyApplicationDto;
import org.plteco.ploytechcourse.domain.application.model.TechCourseForm;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

public interface ApplyApplicationService {
    TechCourseForm apply(ApplyApplicationDto applyApplicationDto);
    boolean isValidDuplicationStudent(Long userId);
}
