package org.plteco.ploytechcourse.domain.grading.repository;

import org.plteco.ploytechcourse.domain.grading.model.GradingForm;
import org.plteco.ploytechcourse.domain.grading.model.GradingPresentationOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GradingPresentationOrderRepository extends JpaRepository<GradingPresentationOrder, Long> {
    List<GradingPresentationOrder> findByGradingFormIdAndStudentIdIn(Long gradingFormId, Collection<Long> studentIds);

    List<GradingPresentationOrder> findByGradingFormId(Long gradingForm_id);
    
    void deleteByGradingForm(GradingForm gradingForm);
}
