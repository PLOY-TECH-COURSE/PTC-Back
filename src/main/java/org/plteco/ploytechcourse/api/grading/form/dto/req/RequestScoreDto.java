package org.plteco.ploytechcourse.api.grading.form.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RequestScoreDto {
    @JsonProperty("student_id")
    @NotNull(message = "student_id가 null입니다.")
    private Long studentId;

    @NotEmpty(message = "answers가 유효하지 않습니다.")
    private List<Answer> answers;

    @Data
    public static class Answer {
        @JsonProperty("question_id")
        @NotNull(message = "question_id가 null입니다.")
        private Long questionId;
        @NotNull(message = "score가 null입니다.")
        private Long score;
    }
}
