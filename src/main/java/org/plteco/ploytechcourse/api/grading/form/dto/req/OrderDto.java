package org.plteco.ploytechcourse.api.grading.form.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "발표 순서 정보 DTO")
public class OrderDto {
    @JsonProperty("student_id")
    @Schema(
        description = "학생 ID",
        required = true
    )
    private Long studentId;
    
    @Schema(
        description = "발표 순서 (1 이상의 정수)",
        minimum = "1",
        required = true
    )
    private Integer order;
}
