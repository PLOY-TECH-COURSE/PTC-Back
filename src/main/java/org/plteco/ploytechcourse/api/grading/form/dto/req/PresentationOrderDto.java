package org.plteco.ploytechcourse.api.grading.form.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "발표 순서 업데이트 요청 DTO")
public class PresentationOrderDto {
    @Schema(
        description = "발표 순서 목록",
        required = true
    )
    private List<OrderDto> orders;
}
