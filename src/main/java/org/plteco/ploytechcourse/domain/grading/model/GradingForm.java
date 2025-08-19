package org.plteco.ploytechcourse.domain.grading.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.plteco.ploytechcourse.domain.application.model.Student;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "grading_forms")
public class GradingForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false, name = "grader_count")
    private Integer graderCount;

    @Column(nullable = false, name = "expected_total_answers")
    private Integer expectedTotalAnswers;

    @Column(name = "is_completed")
    private boolean completed;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "gradingForm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("questionOrder ASC")
    private List<GradingQuestion> gradingQuestions = new ArrayList<>();

    @Getter
    @Builder.Default
    @OneToMany(mappedBy = "form", fetch = FetchType.LAZY)
    private List<GradingAnswer> answers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "gradingForm", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<GradingPresentationOrder> presentationOrders = new ArrayList<>();

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

    public int findGradedStudentsCount(Long graderId){
        return this.presentationOrders.size();
    }

    /** 편의 메서드 */
    public Optional<GradingPresentationOrder> findOrderByStudentId(Long studentId) {
        return presentationOrders.stream()
                .filter(o -> Objects.equals(o.getStudent().getId(), studentId))
                .findFirst();
    }

    public void setOrCreateOrderFor(Student studentRef, int orderIndex) {
        var current = findOrderByStudentId(studentRef.getId());
        if (current.isPresent()) {
            current.get().changeOrder(orderIndex);
            return;
        }
        // 없으면 신규 추가
        var created = new GradingPresentationOrder(this, studentRef, orderIndex);
        presentationOrders.add(created);
    }

    /** 최종 중복 검증 (option) */
    public void assertNoDuplicateOrderIndex() {
        // 이미 들어있는 것
        Set<Integer> seen = new HashSet<>();
        // 중복되는 것
        Set<Integer> dup = new HashSet<>();

        for (GradingPresentationOrder o : presentationOrders) {
            Integer idx = o.getOrderIndex();
            // seen.add(idx)가 성공하면 처음 들어가는 것 반환타입은 bool
            if (idx != null && !seen.add(idx)) dup.add(idx);
        }
        if (!dup.isEmpty()) {
            throw new PltecoException("최종 순서 중복: " + dup, HttpStatus.CONFLICT);
        }
    }

    /** 평가 완료 상태로 변경 */
    public void markAsCompleted() {
        this.completed = true;
    }

}
