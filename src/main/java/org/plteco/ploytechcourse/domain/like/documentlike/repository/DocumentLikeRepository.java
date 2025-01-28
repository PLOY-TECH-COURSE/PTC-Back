package org.plteco.ploytechcourse.domain.like.documentlike.repository;

import org.plteco.ploytechcourse.domain.like.documentlike.model.DocumentLike;
import org.plteco.ploytechcourse.domain.like.documentlike.model.DocumentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentLikeRepository extends JpaRepository<DocumentLike, DocumentLikeId> {
    boolean existsByDocumentIdAndUserId(Long documentId,Long userId);
    long countByDocumentId(Long documentId);
}
