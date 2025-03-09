package org.plteco.ploytechcourse.domain.lesson.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.application.model.Student;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tech_course")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Tech_course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long generation;

    private String name;

    private String explanation;

    @Builder.Default
    @OneToMany(mappedBy = "generation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Track> tracks = new ArrayList<>();

}
