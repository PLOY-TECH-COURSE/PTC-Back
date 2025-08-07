package org.plteco.ploytechcourse.api.grade.dto.res;

import lombok.Data;
import org.plteco.ploytechcourse.domain.grading.model.GradingForm;

import java.time.LocalDate;

@Data
public class GradingFormResponseDto {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDate createdAt;

    public static GradingFormResponseDto fromGradingForm(GradingForm gradingForm) {
        GradingFormResponseDto dto = new GradingFormResponseDto();
        dto.setId(gradingForm.getId());
        dto.setTitle(gradingForm.getTitle());
        dto.setDescription(gradingForm.getDescription());
        dto.setCompleted(gradingForm.isCompleted());
        // createdAt이 LocalDateTime이면 LocalDate로 변환
        dto.setCreatedAt(gradingForm.getCreatedAt().toLocalDate());
        return dto;
    }
}