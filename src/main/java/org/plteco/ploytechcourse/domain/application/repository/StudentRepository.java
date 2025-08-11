package org.plteco.ploytechcourse.domain.application.repository;

import org.plteco.ploytechcourse.domain.application.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s.generation FROM Student s WHERE s.user.id = :userId")
    Optional<Integer> findTechCourseIdByUserId(@Param("userId") Long userId);

    @Query("""
    SELECT s FROM Student s
    WHERE s.generation = (
        SELECT MAX(s2.generation) FROM Student s2
    )
""") // 학생 중 기수(generation)이 제일 높은 학생들만 출력
    List<Student> findAllByLatestGeneration();

    @Query("""
    SELECT count(s) FROM Student s
    WHERE s.generation = (
        SELECT MAX(s2.generation) FROM Student s2
    )
""") // 학생 중 기수(generation)이 제일 높은 학생들 수 출력
    Integer countByLatestGeneration();

    boolean existsByUserId(Long userId);
}
