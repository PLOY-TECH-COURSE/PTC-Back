package org.plteco.ploytechcourse.api.grading.form.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "평가 폼 생성 요청 DTO")
public class CreateFormDto {
    @Schema(description = "평가 폼 제목", example = "프로젝트 평가")
    @NotBlank(message = "제목을 입력해야 합니다.")
    private String title;

    @Schema(description = "설명", example = "중간 발표입니다.")
    @NotBlank(message = "설명을 입력해야합니다.")
    private String description;

    @Schema(description = "평가자 수", example = "3")
    @Min(value = 1, message = "총 채점자 수는 1명 이상이여야 합니다.")
    private Integer grader_counts;

    @Schema(description = "평가 질문 목록")
    @NotEmpty(message = "질문이 추가되지 않았습니다.")
    private List<CreateQuestion> questions;
}
