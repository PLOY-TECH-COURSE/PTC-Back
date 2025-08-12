package org.plteco.ploytechcourse.api.grading.form.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentScoreDto {
    private Long studentId;
    private String studentName;
    private Double averageScore;
    private Integer totalScore;
    private Integer answerCount;
    private Integer rank;
    
    public static StudentScoreDto of(Long studentId, String studentName, Double averageScore, Integer totalScore, Integer answerCount) {
        return StudentScoreDto.builder()
                .studentId(studentId)
                .studentName(studentName)
                .averageScore(averageScore)
                .totalScore(totalScore)
                .answerCount(answerCount)
                .build();
    }
}