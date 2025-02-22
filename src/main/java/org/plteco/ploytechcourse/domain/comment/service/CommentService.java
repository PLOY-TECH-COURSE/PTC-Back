package org.plteco.ploytechcourse.domain.comment.service;

import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.util.List;

public interface CommentService {
    Comment getComment(long commentId);
    void deleteCommentByUser(Comment comment, User user);
    void updateComment(User user, Comment comment, String commentText);
}
