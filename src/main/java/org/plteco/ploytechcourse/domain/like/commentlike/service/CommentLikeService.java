package org.plteco.ploytechcourse.domain.like.commentlike.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.like.commentlike.model.entity.CommentLike;
import org.plteco.ploytechcourse.domain.like.commentlike.repository.CommentLikeRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeService{

    private final CommentLikeRepository commentLikeRepository;

    public void addLike(Comment comment, User user) {
        CommentLike commentLike = CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();

        commentLikeRepository.save(commentLike);
    }

    public void removeLike(Comment comment, User user) {
        CommentLike commentLike = CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();

        commentLikeRepository.deleteById(commentLike.getId());
    }

    public boolean isLiked(Comment comment, User user) {
        CommentLike commentLike = CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();

        return commentLikeRepository.existsById(commentLike.getId());
    }

    public long getLikes(Comment comment) {
        return commentLikeRepository.countByIdCommentId(comment.getId());
    }
}
