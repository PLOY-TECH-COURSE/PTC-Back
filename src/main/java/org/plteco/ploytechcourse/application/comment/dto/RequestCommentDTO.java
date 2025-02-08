package org.plteco.ploytechcourse.application.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RequestCommentDTO {
    @Schema(description = "댓글 데이터", example = "어느 옛날옛적에 대머리인 허동운과 허온이 살았더래요")
    @NotBlank(message = "댓글 내용이 작성되지 않았습니다.")
    @Size(max = 500, message = "댓글은 500자를 초과할 수 없습니다.")
    String commentText;
}
