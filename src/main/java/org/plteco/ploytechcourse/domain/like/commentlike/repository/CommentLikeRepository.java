package org.plteco.ploytechcourse.domain.like.commentlike.repository;

import org.plteco.ploytechcourse.domain.like.commentlike.model.entity.CommentLike;
import org.plteco.ploytechcourse.domain.like.commentlike.model.entity.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {
    long countByCommentId(Long commentId);
    boolean existsByCommentIdAndUserId(long commentId, long userId);

    @Query("SELECT COUNT(cl) FROM CommentLike cl WHERE cl.comment.user.id = :userId")
    Long countTotalLikesByUserId(@Param("userId") Long userId);

}
