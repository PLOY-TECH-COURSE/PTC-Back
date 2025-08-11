package org.plteco.ploytechcourse.domain.grading.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "grading_questions")
public class GradingQuestion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private GradingForm gradingForm;

    @Getter
    @Column(name = "question_text")
    private String question;

    @Getter
    private Integer questionOrder;

    @Getter
    @Builder.Default
    @OneToMany(mappedBy = "gradingQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradingScore> gradingScores = new ArrayList<>();;
}
