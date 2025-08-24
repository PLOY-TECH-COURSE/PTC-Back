package org.plteco.ploytechcourse.api.grading.form.dto.res;

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

    public static GradingFormResponseDto fromGradingForm(GradingForm gradingForm, Long graderId, boolean graderCompleted) {
        GradingFormResponseDto dto = new GradingFormResponseDto();
        dto.setId(gradingForm.getId());
        dto.setTitle(gradingForm.getTitle());
        dto.setDescription(gradingForm.getDescription());
        // 폼 모든 채점이 끝났는가? or 채점자의 모든 채점이 끝났는가?
        dto.setCompleted(gradingForm.isCompleted() || graderCompleted);
        // createdAt이 LocalDateTime 이면 LocalDate로 변환
        dto.setCreatedAt(gradingForm.getCreatedAt().toLocalDate());
        return dto;
    }
}