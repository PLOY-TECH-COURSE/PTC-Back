package org.plteco.ploytechcourse.application.grading.service;

import org.plteco.ploytechcourse.api.grading.form.dto.req.CreateFormDto;
import org.plteco.ploytechcourse.api.grading.form.dto.req.PresentationOrderDto;
import org.plteco.ploytechcourse.api.grading.form.dto.req.RequestScoreDto;
import org.plteco.ploytechcourse.api.grading.form.dto.res.GradingFormDetailResponseDto;
import org.plteco.ploytechcourse.api.grading.form.dto.res.GradingFormResponseDto;
import org.plteco.ploytechcourse.api.grading.form.dto.res.PresentationOrderResponseDto;
import org.plteco.ploytechcourse.api.grading.form.dto.res.StudentScoreDto;
import org.plteco.ploytechcourse.application.grading.command.PresentationOrderCommand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GradingServiceApplication {
    void createGradingForm(CreateFormDto createFormDto);
    List<GradingFormResponseDto> getAllGradingForm();
    void applyPresentationOrders(Long formId, PresentationOrderCommand cmd);
    List<PresentationOrderResponseDto> getPresentationOrder(Long formId);
    GradingFormDetailResponseDto getGradingFormByFormId(Long formId);
    void addScore(Long formId, RequestScoreDto scoreDto);
    void deleteGradingForm(Long formId);
}
