package org.plteco.ploytechcourse.domain.comment.service;

import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getComments(long document_id);
    void createComment(long user_id, long document_id, String commentText);
    void deleteComment(long commentId);
    void updateComment(long commentId, String commentText);
}
