package org.plteco.ploytechcourse.domain.like.commentlike.repository;

import org.plteco.ploytechcourse.domain.like.commentlike.model.entity.CommentLike;
import org.plteco.ploytechcourse.domain.like.commentlike.model.entity.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {
    long countByCommentId(Long commentId);
    boolean existsByCommentIdAndUserId(long commentId, long userId);
    void deleteByCommentId(long commentId);
}
