package org.plteco.ploytechcourse.domain.grading.repository;

import org.plteco.ploytechcourse.domain.grading.model.GradingScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradingScoreRepository extends JpaRepository<GradingScore, Long> {

}