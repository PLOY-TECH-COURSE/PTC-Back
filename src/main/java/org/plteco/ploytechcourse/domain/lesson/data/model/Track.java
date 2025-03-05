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
@Table(name = "track")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "generation", referencedColumnName = "generation")
    private Tech_course generation;

    private String name;

    private String explanation;

    @Builder.Default
    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassEntity> classEntities = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();
}
