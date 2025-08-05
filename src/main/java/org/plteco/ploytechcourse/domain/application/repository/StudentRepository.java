package org.plteco.ploytechcourse.domain.application.repository;

import org.plteco.ploytechcourse.domain.application.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s.generation FROM Student s WHERE s.user.id = :userId")
    Optional<Integer> findTechCourseIdByUserId(@Param("userId") Long userId);

    boolean existsByUserId(Long userId);
}
