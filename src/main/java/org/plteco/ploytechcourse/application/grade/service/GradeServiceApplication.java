package org.plteco.ploytechcourse.application.grade.service;

import org.plteco.ploytechcourse.application.grade.dto.CreateFormDto;
import org.springframework.stereotype.Service;

@Service
public interface GradeServiceApplication {
    void createGradeForm(CreateFormDto createFormDto);
}
