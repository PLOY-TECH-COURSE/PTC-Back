package org.plteco.ploytechcourse.domain.comment.repository;

import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
