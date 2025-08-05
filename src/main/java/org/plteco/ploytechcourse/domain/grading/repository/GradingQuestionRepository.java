package org.plteco.ploytechcourse.domain.grading.repository;

import org.plteco.ploytechcourse.domain.grading.model.GradingForm;
import org.plteco.ploytechcourse.domain.grading.model.GradingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradingQuestionRepository extends JpaRepository<GradingQuestion, Long> {

}