package org.plteco.ploytechcourse.domain.grading.repository;

import org.plteco.ploytechcourse.domain.grading.model.GradingAnswer;
import org.plteco.ploytechcourse.domain.grading.model.GradingForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GradingAnswerRepository extends JpaRepository<GradingAnswer, Long> {
    @Query("SELECT DISTINCT a.student.id FROM GradingAnswer a WHERE a.form.id = :formId")
    Set<Long> findGradedStudentIdsByFormId(@Param("formId") Long formId);

    boolean existsByFormIdAndStudentId(Long gradingFormId, Long studentId);
    
    // 특정 평가자가 특정 학생을 이미 평가했는지 확인
    @Query("SELECT COUNT(a) > 0 FROM GradingAnswer a WHERE a.form.id = :formId AND a.grader.id = :graderId AND a.student.id = :studentId")
    boolean existsByFormIdAndGraderIdAndStudentId(@Param("formId") Long formId, @Param("graderId") Long graderId, @Param("studentId") Long studentId);
    
    // 특정 폼의 총 답변 수 조회
    @Query("SELECT COUNT(a) FROM GradingAnswer a WHERE a.form.id = :formId")
    long countByFormId(@Param("formId") Long formId);
    
    // 특정 폼의 학생별 점수 통계 조회
    @Query("""
        SELECT a.student.id as studentId,
               a.student.user.name as studentName,
               AVG(CAST(a.score AS double)) as averageScore,
               SUM(a.score) as totalScore,
               COUNT(a) as answerCount
        FROM GradingAnswer a 
        WHERE a.form.id = :formId 
        GROUP BY a.student.id, a.student.user.name
        ORDER BY AVG(CAST(a.score AS double)) DESC, SUM(a.score) DESC
    """)
    List<StudentScoreProjection> findStudentScoresByFormId(@Param("formId") Long formId);
    
    // 특정 폼에서 특정 학생의 모든 답변 조회
    @Query("SELECT a FROM GradingAnswer a WHERE a.form.id = :formId AND a.student.id = :studentId")
    List<GradingAnswer> findByFormIdAndStudentId(@Param("formId") Long formId, @Param("studentId") Long studentId);
    
    // 평가 폼 삭제를 위한 답변 일괄 삭제
    void deleteByForm(GradingForm gradingForm);
    
    // Projection 인터페이스
    interface StudentScoreProjection {
        Long getStudentId();
        String getStudentName();
        Double getAverageScore();
        Long getTotalScore();
        Long getAnswerCount();
    }
}