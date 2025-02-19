package org.plteco.ploytechcourse.domain.image.repository;

import org.plteco.ploytechcourse.domain.image.data.entity.RandomBucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomBucketRepository extends JpaRepository<RandomBucket, Long> {
}
