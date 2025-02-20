package org.plteco.ploytechcourse.domain.like.documentlike.service;


import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.like.documentlike.model.DocumentLike;
import org.plteco.ploytechcourse.domain.like.documentlike.model.DocumentLikeId;
import org.plteco.ploytechcourse.domain.like.documentlike.repository.DocumentLikeRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocumentLikeService{

    private final DocumentLikeRepository documentLikeRepository;

    @Transactional
    public void addLike(Document document, User user) {
        DocumentLikeId id = createDocumentLikeId(document, user);

        if (documentLikeRepository.existsById(id)) {
            throw new PltecoException("이미 좋아요를 눌렀습니다.", HttpStatus.CONFLICT);
        }

        DocumentLike documentLike = DocumentLike.builder()
                .document(document)
                .user(user)
                .build();

        documentLikeRepository.save(documentLike);
        document.increaseLike();
    }

    @Transactional
    public void removeLike(Document document, User user) {
        DocumentLikeId id = createDocumentLikeId(document, user);

        DocumentLike documentLike = documentLikeRepository.findById(id)
                .orElseThrow(() -> new PltecoException("좋아요를 누르지 않았습니다.", HttpStatus.NOT_FOUND));

        documentLikeRepository.delete(documentLike);
        document.decreaseLike();
    }


    @Transactional(readOnly = true)
    public boolean isLiked(Document document, User user) {
        DocumentLikeId id = new DocumentLikeId(document.getId(), user.getId());

        return documentLikeRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public long getLikes(long documentId) {
        return documentLikeRepository.countByDocumentId(documentId);
    }

    private DocumentLikeId createDocumentLikeId(Document document, User user) {
        return new DocumentLikeId(document.getId(), user.getId());
    }

}
