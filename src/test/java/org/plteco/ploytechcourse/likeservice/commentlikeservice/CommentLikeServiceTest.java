package org.plteco.ploytechcourse.likeservice.commentlikeservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.plteco.ploytechcourse.domain.like.commentlike.model.entity.CommentLike;
import org.plteco.ploytechcourse.domain.like.commentlike.model.entity.CommentLikeId;
import org.plteco.ploytechcourse.domain.like.commentlike.repository.CommentLikeRepository;
import org.plteco.ploytechcourse.domain.like.commentlike.service.CommentLikeService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentLikeServiceTest {

    @Mock
    private CommentLikeRepository commentLikeRepository;

    @InjectMocks
    private CommentLikeService commentLikeService;

    @Test
    public void addLikeTest() {
        // given
        long commentId = 1L;
        long userId = 1L;
        CommentLikeId commentLikeId = CommentLikeId.builder()
                .commentId(commentId)
                .userId(userId)
                .build();
        CommentLike commentLike = CommentLike.builder()
                .id(commentLikeId)
                .build();

        // when
        commentLikeService.addLike(commentId, userId);

        // then
        verify(commentLikeRepository, times(1)).save(commentLike);
    }

    @Test
    public void removeLikeTest() {
        // given
        long commentId = 1L;
        long userId = 1L;
        CommentLikeId commentLikeId = CommentLikeId.builder()
                .commentId(commentId)
                .userId(userId)
                .build();

        // when
        commentLikeService.removeLike(commentId, userId);

        // then
        verify(commentLikeRepository, times(1)).deleteById(commentLikeId);
    }

    @Test
    public void isLikedTest() {
        // given
        long commentId = 1L;
        long userId = 1L;
        when(commentLikeRepository.existsByCommentIdAndUserId(commentId, userId)).thenReturn(true);

        // when
        boolean isLiked = commentLikeService.isLiked(commentId, userId);

        // then
        verify(commentLikeRepository, times(1)).existsByCommentIdAndUserId(commentId, userId);
        assert(isLiked); // expected true
    }

    @Test
    public void getLikesTest() {
        // given
        long commentId = 1L;
        when(commentLikeRepository.countByCommentId(commentId)).thenReturn(5L);

        // when
        long count = commentLikeService.getLikes(commentId);

        // then
        verify(commentLikeRepository, times(1)).countByCommentId(commentId);
        assert(count == 5L); // expected 5
    }
}
