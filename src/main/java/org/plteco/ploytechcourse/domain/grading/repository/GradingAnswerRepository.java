package org.plteco.ploytechcourse.domain.grading.repository;

import org.plteco.ploytechcourse.domain.grading.model.GradingAnswer;
import org.plteco.ploytechcourse.domain.grading.model.GradingForm;
import org.plteco.ploytechcourse.domain.grading.model.GradingQuestion;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.application.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradingAnswerRepository extends JpaRepository<GradingAnswer, Long> {

}