package org.plteco.ploytechcourse.api.grading.form.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({ "form_id", "title", "student_id","grader_counts", "description", "questions" })
public class GradingFormDetailResponseDto {
    @JsonProperty("form_id")
    private Long formId;

    private String title;

    @JsonProperty("student_id")
    private Long studentId;

    @JsonProperty("grader_counts")
    private Integer graderCounts;

    private String description;

    private List<ResponseQuestionDto> questions;

    public static GradingFormDetailResponseDto from(Long formId, Long studentId,String title, Integer graderCounts,
                                                    String description, List<ResponseQuestionDto> questions) {
        GradingFormDetailResponseDto dto = new GradingFormDetailResponseDto();
        dto.formId = formId;
        dto.studentId = studentId;
        dto.title = title;
        dto.graderCounts = graderCounts;
        dto.description = description;
        dto.questions = questions;
        return dto;
    }

    @Data
    public static class ResponseQuestionDto{
        private Long questionId;
        private String question;
        private List<Integer> scores;

        public static ResponseQuestionDto from(Long questionId, String question, List<Integer> scores) {
            ResponseQuestionDto dto = new ResponseQuestionDto();
            dto.questionId = questionId;
            dto.question = question;
            dto.scores = scores;
            return dto;
        }
    }

}
