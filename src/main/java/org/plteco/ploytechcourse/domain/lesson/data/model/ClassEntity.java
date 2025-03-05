package org.plteco.ploytechcourse.domain.lesson.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "class")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Track의 id를 그대로 기본 키로 사용

    @ManyToOne
    @JoinColumn(name = "track_id", referencedColumnName = "id")
    private Track track;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "thumbnail", length = 200)
    private String thumbnail;

    @Column(name = "explanation")
    private String explanation;
}
