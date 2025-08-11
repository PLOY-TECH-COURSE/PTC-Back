package org.plteco.ploytechcourse.api.grading.form.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "메시지 응답 DTO")
public class MessageResponse {
    @Schema(
        description = "응답 메시지",
        required = true
    )
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}