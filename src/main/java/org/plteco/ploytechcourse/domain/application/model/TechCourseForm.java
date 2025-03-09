package org.plteco.ploytechcourse.domain.application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.plteco.ploytechcourse.domain.lesson.data.model.Track;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "tech_course_form")
@Getter
@NoArgsConstructor
public class TechCourseForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(columnDefinition = "TEXT")
    private String skill;

    @Column(columnDefinition = "TEXT")
    private String study;

    @Column(columnDefinition = "TEXT")
    private String expectation;

    @Builder
    public TechCourseForm(Long id, User user, String introduction, String skill,  String study, String expectation) {
        this.id = id;
        this.user = user;
        this.introduction = introduction;
        this.skill = skill;
        this.study = study;
        this.expectation = expectation;
    }
}