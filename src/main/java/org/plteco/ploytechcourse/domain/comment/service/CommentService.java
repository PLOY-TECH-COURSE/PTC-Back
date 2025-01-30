package org.plteco.ploytechcourse.domain.comment.service;

import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> getComments(Long document_id);
    void createComment(Long document_id, String commentText);
    void deleteComment(Long commentId);
    void updateComment(Long commentId, String commentText);
    Comment getCommentById(Long commentId);
}
