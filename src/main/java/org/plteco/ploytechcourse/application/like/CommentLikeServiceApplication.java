package org.plteco.ploytechcourse.application.like;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.document.service.DocumentCommentServiceImpl;
import org.plteco.ploytechcourse.domain.like.commentlike.service.CommentLikeService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceApplication {

    private final CommentLikeService commentLikeService;

    private final UserContextUtil userContextUtil;
    private final DocumentCommentServiceImpl commentService;

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
