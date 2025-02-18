package org.plteco.ploytechcourse.domain.like.commentlike.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.like.commentlike.model.entity.CommentLike;
import org.plteco.ploytechcourse.domain.like.commentlike.model.entity.CommentLikeId;
import org.plteco.ploytechcourse.domain.like.commentlike.repository.CommentLikeRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeService{

    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public void addLike(Comment comment, User user) {
        if (commentLikeRepository.existsByCommentIdAndUserId(comment.getId(), user.getId())) {
            throw new PltecoException("이미 좋아요를 눌렀습니다.",HttpStatus.CONFLICT);
        }
        CommentLike commentLike = createCommentLike(comment, user);
        commentLikeRepository.save(commentLike);
    }

    @Transactional
    public void unLike(Comment comment, User user) {
        CommentLike commentLike = createCommentLike(comment, user);

        if (!commentLikeRepository.existsById(commentLike.getId())) {
            throw new PltecoException("좋아요를 누르지 않았습니다.", HttpStatus.NOT_FOUND);
        }

        commentLikeRepository.delete(commentLike);
    }

    @Transactional
    public void deleteLikeByCommentId(long commentId) {
        commentLikeRepository.deleteByCommentId(commentId);
    }

    @Transactional(readOnly = true)
    public boolean isLiked(Comment comment, User user) {
        CommentLikeId id = new CommentLikeId(comment.getId(), user.getId());

        return commentLikeRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public long getLikes(Comment comment) {
        return commentLikeRepository.countByCommentId(comment.getId());
    }

    private CommentLike createCommentLike(Comment comment, User user) {
        return CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();
    }
}
