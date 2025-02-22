package org.plteco.ploytechcourse.domain.document.service;

import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.util.List;

public interface DocumentCommentService {
    List<Comment> getComments(Document document);
    void createComment(User user, Document document, String commentText);
}
