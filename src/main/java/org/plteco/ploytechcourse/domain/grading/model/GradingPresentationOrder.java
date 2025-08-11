package org.plteco.ploytechcourse.domain.grading.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.plteco.ploytechcourse.domain.application.model.Student;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;

@Entity
@Getter
@Setter
@Table(name = "grading_order")
public class GradingPresentationOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private GradingForm gradingForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "order_index")
    private Integer orderIndex;

    public GradingPresentationOrder() {}

    public GradingPresentationOrder(GradingForm gradingForm, Student student, int i) {
        this.gradingForm = gradingForm;
        this.student = student;
        this.orderIndex = i;
    }

    public void changeOrder(int orderIndex) {
        if (orderIndex <= 0) {
            throw new PltecoException("순서는 1 이상의 정수여야 합니다.", HttpStatus.BAD_REQUEST);
        }
        this.orderIndex = orderIndex;
    }
}
