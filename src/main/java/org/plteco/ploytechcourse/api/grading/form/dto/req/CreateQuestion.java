package org.plteco.ploytechcourse.api.grading.form.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "평가 질문 DTO")
public class CreateQuestion {
    @Schema(description = "평가 질문", example = "코드 품질은 어떠한가요?")
    private String question;
    
    @Schema(description = "점수 옵션 목록", example = "[1, 2, 3, 4, 5]")
    private List<Integer> scores;
}
