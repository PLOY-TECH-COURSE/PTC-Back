package org.plteco.ploytechcourse.domain.application.repository;

import org.plteco.ploytechcourse.domain.application.model.TechCourseForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<TechCourseForm, Long> {
    boolean existsByUserId(Long uid);
}
