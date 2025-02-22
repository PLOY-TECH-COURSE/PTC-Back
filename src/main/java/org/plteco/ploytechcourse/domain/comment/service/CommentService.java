package org.plteco.ploytechcourse.domain.comment.service;

import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

public interface CommentService {
    Comment getComment(long commentId);
    void updateComment(User user, Comment comment, String commentText);
}
