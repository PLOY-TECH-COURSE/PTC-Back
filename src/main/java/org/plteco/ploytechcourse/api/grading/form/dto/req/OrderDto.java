package org.plteco.ploytechcourse.api.grading.form.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "발표 순서 정보 DTO")
public class OrderDto {
    @JsonProperty("student_id")
    @Schema(
        description = "학생 ID",
        required = true
    )
    @NotNull(message = "student_id가 입력되지 않았습니다.")
    private Long studentId;
    
    @Schema(
        description = "발표 순서 (1 이상의 정수)",
        minimum = "1",
        required = true
    )
    @Min(value = 1)
    private Integer order;
}
