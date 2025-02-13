package org.plteco.ploytechcourse.domain.like.documentlike.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.like.documentlike.model.DocumentLike;
import org.plteco.ploytechcourse.domain.like.documentlike.repository.DocumentLikeRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentLikeService{

    private final DocumentLikeRepository repository;

    public void addLike(Document document, User user) {

        DocumentLike documentLike = DocumentLike.builder()
                .document(document)
                .user(user)
                .build();

        repository.save(documentLike);
    }

    public void removeLike(Document document, User user) {
        DocumentLike documentLike = DocumentLike.builder()
                .document(document)
                .user(user)
                .build();

        repository.deleteById(documentLike.getId());
    }

    public boolean isLiked(Document document, User user) {
        DocumentLike documentLike = DocumentLike.builder()
                .document(document)
                .user(user)
                .build();

        return repository.existsById(documentLike.getId());
    }

    public long getLikes(long documentId) {
        return repository.countByDocumentId(documentId);
    }
}
