package org.plteco.ploytechcourse.domain.grading.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "grading_question_scores")
public class GradingScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private GradingQuestion gradingQuestion;

    private Integer scoreValue;

    private Integer scoreOrder;
}
