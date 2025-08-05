package org.plteco.ploytechcourse.domain.grading.repository;

import org.plteco.ploytechcourse.domain.application.model.Student;
import org.plteco.ploytechcourse.domain.grading.model.GradingForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GradingFormRepository extends JpaRepository<GradingForm, Long> {

}
