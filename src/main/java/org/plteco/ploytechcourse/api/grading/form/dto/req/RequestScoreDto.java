package org.plteco.ploytechcourse.api.grading.form.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RequestScoreDto {
    @JsonProperty("student_id")
    private Long studentId;

    private List<Answer> answers;

    @Data
    public static class Answer {
        @JsonProperty("question_id")
        private Long questionId;
        private Long score;
    }
}
