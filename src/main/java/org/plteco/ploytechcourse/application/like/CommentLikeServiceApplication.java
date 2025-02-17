package org.plteco.ploytechcourse.application.like;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.comment.service.CommentServiceImpl;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.like.commentlike.service.CommentLikeService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceApplication {

    private final CommentLikeService commentLikeService;

    private final UserContextUtil userContextUtil;
    private final CommentServiceImpl commentService;

    private User getCurrentUser() {
        return userContextUtil.getCurrentUser();
    }

    private Comment getComment(long commentId) {
        return commentService.getComment(commentId);
    }

    public void addLike(long commentId) {
        User user = getCurrentUser();
        Comment comment = getComment(commentId);
        commentLikeService.addLike(comment, user);
    }

    public void unLike(long commentId) {
        User user = getCurrentUser();
        Comment comment = getComment(commentId);
        commentLikeService.unLike(comment, user);
    }

}
