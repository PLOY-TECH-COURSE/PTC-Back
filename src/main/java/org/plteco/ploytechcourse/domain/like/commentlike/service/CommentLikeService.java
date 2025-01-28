package org.plteco.ploytechcourse.domain.like.commentlike.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.like.commentlike.model.entity.CommentLike;
import org.plteco.ploytechcourse.domain.like.commentlike.model.entity.CommentLikeId;
import org.plteco.ploytechcourse.domain.like.commentlike.repository.CommentLikeRepository;
import org.plteco.ploytechcourse.domain.like.service.LikeService;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeService implements LikeService {

    private final CommentLikeRepository repository;

    public void addLike(long commentId, long userId) {

        CommentLikeId commentLikeId = CommentLikeId.builder()
                .commentId(commentId)
                .userId(userId)
                .build();

        CommentLike commentLike = CommentLike.builder()
                .id(commentLikeId)
                .build();

        repository.save(commentLike);
    }

    public void removeLike(long commentId, long userId) {
        CommentLikeId commentLikeId = CommentLikeId.builder()
                .commentId(commentId)
                .userId(userId)
                .build();

        repository.deleteById(commentLikeId);
    }

    public boolean isLiked(long commentId, long userId) {
        return repository.existsByCommentIdAndUserId(commentId,userId);
    }

    public long getLikes(long commentId) {
        return repository.countByIdCommentId(commentId);
    }
}
