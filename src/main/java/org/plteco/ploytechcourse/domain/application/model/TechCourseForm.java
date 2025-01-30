package org.plteco.ploytechcourse.domain.application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

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
    private String resolution;

    @Builder
    public TechCourseForm(User user, String introduction, String resolution) {
        this.user = user;
        this.introduction = introduction;
        this.resolution = resolution;
    }
}