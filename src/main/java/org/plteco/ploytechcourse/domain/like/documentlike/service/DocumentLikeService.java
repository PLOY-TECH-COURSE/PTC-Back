package org.plteco.ploytechcourse.domain.like.documentlike.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.like.documentlike.model.DocumentLike;
import org.plteco.ploytechcourse.domain.like.documentlike.model.DocumentLikeId;
import org.plteco.ploytechcourse.domain.like.documentlike.repository.DocumentLikeRepository;
import org.plteco.ploytechcourse.domain.like.service.LikeService;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentLikeService implements LikeService {

    private final DocumentLikeRepository repository;

    public void addLike(long documentId, long userId) {

        DocumentLikeId documentLikeId = DocumentLikeId.builder()
                .documentId(documentId)
                .userId(userId)
                .build();

        DocumentLike documentLike = DocumentLike.builder()
                .id(documentLikeId)
                .build();

        repository.save(documentLike);
    }

    public void removeLike(long documentId, long userId) {
        DocumentLikeId documentLikeId = DocumentLikeId.builder()
                .documentId(documentId)
                .userId(userId)
                .build();

        repository.deleteById(documentLikeId);
    }

    public boolean isLiked(long documentId, long userId) {
        return repository.existsByDocumentIdAndUserId(documentId,userId);
    }

    public long getLikes(long documentId) {
        return repository.countByDocumentId(documentId);
    }
}
