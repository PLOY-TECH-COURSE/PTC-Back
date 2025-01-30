package org.plteco.ploytechcourse.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.application.model.TechCourseForm;
import org.plteco.ploytechcourse.domain.application.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowApplicationServiceImpl implements ShowApplicationService {

    private final ApplicationRepository applicationRepository;

    @Override
    public List<TechCourseForm> getTechCourses() {
        return applicationRepository.findAll();
    }
}
