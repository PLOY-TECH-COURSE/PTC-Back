package org.plteco.ploytechcourse.domain.like.documentlike.repository;

import org.plteco.ploytechcourse.domain.like.documentlike.model.DocumentLike;
import org.plteco.ploytechcourse.domain.like.documentlike.model.DocumentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentLikeRepository extends JpaRepository<DocumentLike, DocumentLikeId> {
    long countByDocumentId(Long documentId);

    @Query("SELECT COALESCE(SUM(d.documentLikeCount), 0) FROM Document d WHERE d.user.id = :userId")
    long getTotalLikesReceivedByUser(@Param("userId") Long userId);

}
