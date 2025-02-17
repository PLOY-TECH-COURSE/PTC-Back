package org.plteco.ploytechcourse.domain.application.repository;

import org.plteco.ploytechcourse.domain.application.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
