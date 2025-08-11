package org.plteco.ploytechcourse.domain.grading.model;

import jakarta.persistence.*;
import lombok.*;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.application.model.Student;
import java.time.LocalDateTime;

@Entity
@Table(name = "grading_answer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GradingAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 채점 폼
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private GradingForm form;

    // 질문
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private GradingQuestion gradingQuestion;

    // 피채점자 (학생)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Getter
    // 채점자 (관리자)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grader_id", nullable = false)
    private User grader;

    // 점수
    @Column(nullable = false)
    private Integer score;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
