package org.plteco.ploytechcourse.domain.comment.service;

import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.util.List;

public interface CommentService {
    List<Comment> getComments(long documentId);
    Comment getComment(long commentId);
    void createComment(User user, Document document, String commentText);
    void deleteCommentByUser(long commentId, User user);
    void updateComment(User user, long commentId, String commentText);
}
