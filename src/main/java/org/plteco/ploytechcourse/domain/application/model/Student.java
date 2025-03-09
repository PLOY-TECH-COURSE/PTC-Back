package org.plteco.ploytechcourse.domain.application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.lesson.data.model.Tech_course;
import org.plteco.ploytechcourse.domain.lesson.data.model.Track;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tech_course_id")
    private Tech_course techCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private Track track;

    @Builder
    public Student(User user, Tech_course techCourse, Track track) {
        this.user = user;
        this.techCourse = techCourse;
        this.track = track;
    }
}
