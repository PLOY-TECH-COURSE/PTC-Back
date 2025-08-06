package org.plteco.ploytechcourse.domain.grading.repository;
import org.plteco.ploytechcourse.domain.grading.model.GradingForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface GradingFormRepository extends JpaRepository<GradingForm, Long> {

}
