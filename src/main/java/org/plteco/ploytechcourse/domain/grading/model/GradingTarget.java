package org.plteco.ploytechcourse.domain.grading.model;

import jakarta.persistence.*;
import org.plteco.ploytechcourse.domain.application.model.Student;

@Entity
@Table(name = "grading_target")
public class GradingTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private GradingForm gradingForm;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "order_index")
    private Integer orderIndex;
}
