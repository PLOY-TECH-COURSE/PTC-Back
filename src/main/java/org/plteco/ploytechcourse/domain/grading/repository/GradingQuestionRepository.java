package org.plteco.ploytechcourse.domain.grading.repository;

import org.plteco.ploytechcourse.domain.grading.model.GradingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradingQuestionRepository extends JpaRepository<GradingQuestion, Long> {

}