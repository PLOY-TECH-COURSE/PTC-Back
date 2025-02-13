package org.plteco.ploytechcourse.domain.lesson.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
