package org.plteco.ploytechcourse.likeservice.documentlikeservice;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.plteco.ploytechcourse.domain.like.documentlike.model.DocumentLike;
import org.plteco.ploytechcourse.domain.like.documentlike.model.DocumentLikeId;
import org.plteco.ploytechcourse.domain.like.documentlike.repository.DocumentLikeRepository;
import org.plteco.ploytechcourse.domain.like.documentlike.service.DocumentLikeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentLikeServiceTest {

    @Mock
    DocumentLikeRepository documentLikeRepository;

    @InjectMocks
    DocumentLikeService documentLikeService;

    @Test
    public void addLikeTest() {
        // given
        long documentId = 1L;
        long userId = 1L;

        // when
        documentLikeService.addLike(documentId, userId);

        // then
        verify(documentLikeRepository, times(1)).save(any(DocumentLike.class));
    }

    @Test
    public void removeLikeTest() {
        // given
        long documentId = 1L;
        long userId = 1L;

        DocumentLikeId documentLikeId = DocumentLikeId.builder()
                .documentId(documentId)
                .userId(userId)
                .build();

        // when
        documentLikeService.removeLike(documentId, userId);

        // then
        verify(documentLikeRepository, times(1)).deleteById(documentLikeId);
    }

    @Test
    public void isLikedTest_true() {
        // given
        long documentId = 1L;
        long userId = 1L;

        // when
        when(documentLikeRepository.existsByDocumentIdAndUserId(documentId, userId)).thenReturn(true);
        boolean isLiked = documentLikeService.isLiked(documentId, userId);

        // then
        verify(documentLikeRepository, times(1)).existsByDocumentIdAndUserId(documentId, userId);
        assert(isLiked);
    }

    @Test
    public void isLikedTest_false() {
        // given
        long documentId = 1L;
        long userId = 1L;

        // when
        when(documentLikeRepository.existsByDocumentIdAndUserId(documentId, userId)).thenReturn(false);
        boolean isLiked = documentLikeService.isLiked(documentId, userId);

        // then
        verify(documentLikeRepository, times(1)).existsByDocumentIdAndUserId(documentId, userId);
        assert(!isLiked);
    }

    @Test
    public void getLikesTest() {
        // given
        long documentId = 1L;

        // when
        when(documentLikeRepository.countByDocumentId(documentId)).thenReturn(10L);
        long count = documentLikeService.getLikes(documentId);

        // then
        verify(documentLikeRepository, times(1)).countByDocumentId(documentId);
        assert(count == 10L);
    }
}
