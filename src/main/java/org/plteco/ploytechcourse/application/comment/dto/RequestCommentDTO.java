package org.plteco.ploytechcourse.application.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RequestCommentDTO {
    @NotBlank(message = "댓글 내용이 작성되지 않았습니다.")
    @Size(max = 500, message = "댓글은 500자를 초과할 수 없습니다.")
    String commentText;
}
