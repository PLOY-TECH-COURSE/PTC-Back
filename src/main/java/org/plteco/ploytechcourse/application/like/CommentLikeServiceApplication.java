package org.plteco.ploytechcourse.application.like;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.comment.service.CommentServiceImpl;
import org.plteco.ploytechcourse.domain.like.commentlike.service.CommentLikeService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceApplication {

    private final CommentLikeService commentLikeService;

    private final UserContextUtil userContextUtil;
    private final CommentServiceImpl commentService;

    public void addLike(long commentId) {
        User user = userContextUtil.getCurrentUser();

        Comment comment = commentService.getComment(commentId);

        commentLikeService.addLike(comment, user);
    }

    public void removeLike(long commentId) {
        User user = userContextUtil.getCurrentUser();

        Comment comment = commentService.getComment(commentId);

        commentLikeService.removeLike(comment, user);
    }

}
