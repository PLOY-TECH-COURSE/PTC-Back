package org.plteco.ploytechcourse.domain.document.repository;

import org.plteco.ploytechcourse.domain.document.model.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {
}