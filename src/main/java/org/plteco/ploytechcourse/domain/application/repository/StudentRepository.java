package org.plteco.ploytechcourse.domain.application.repository;

import org.plteco.ploytechcourse.domain.application.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s.techCourse.id FROM Student s WHERE s.user.id = :userId")
    Optional<Long> findTechCourseIdByUserId(@Param("userId") Long userId);
}
