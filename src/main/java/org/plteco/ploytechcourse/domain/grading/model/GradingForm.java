package org.plteco.ploytechcourse.domain.grading.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "grading_forms")
public class GradingForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false,name="grader_count")
    private Integer graderCount;

    private Integer expectedTotalAnswers;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Getter
    @Builder.Default
    @OneToMany(mappedBy = "gradingForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradingQuestion> gradingQuestions = new ArrayList<>();

    // 질문, 점수 추가
    public void addQuestionWithScores(String content, List<Integer> scores) {
        GradingQuestion question = GradingQuestion.builder()
                .gradingForm(this)
                .question(content)
                .questionOrder(gradingQuestions.size() + 1)
                .build();

        for (int i = 1; i <= scores.size(); i++) {
            GradingScore score = GradingScore.builder()
                    .gradingQuestion(question)
                    .scoreValue(scores.get(i - 1))
                    .scoreOrder(i)  // 순서도 부여
                    .build();
            question.getGradingScores().add(score);
        }

        this.gradingQuestions.add(question);
    }
}
