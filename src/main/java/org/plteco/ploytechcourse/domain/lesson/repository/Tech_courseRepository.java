package org.plteco.ploytechcourse.domain.lesson.repository;

import org.plteco.ploytechcourse.domain.lesson.data.model.Tech_course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Tech_courseRepository extends JpaRepository<Tech_course, Long> {
    Tech_course findByGeneration(Long generation);
}
