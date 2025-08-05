package org.plteco.ploytechcourse.application.grade.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "평가 폼 생성 요청 DTO")
public class CreateFormDto {
    @Schema(description = "평가 폼 제목", example = "프로젝트 평가")
    private String title;
    
    @Schema(description = "평가자 수", example = "3")
    private Integer grader_counts;
    
    @Schema(description = "평가 질문 목록")
    private List<CreateQuestion> questions;
}
