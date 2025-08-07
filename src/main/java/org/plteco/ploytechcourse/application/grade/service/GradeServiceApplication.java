package org.plteco.ploytechcourse.application.grade.service;

import org.plteco.ploytechcourse.api.grade.dto.req.CreateFormDto;
import org.plteco.ploytechcourse.api.grade.dto.res.GradingFormResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GradeServiceApplication {
    void createGradingForm(CreateFormDto createFormDto);
    List<GradingFormResponseDto> getAllGradingForm();
}
