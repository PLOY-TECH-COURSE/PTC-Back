package org.plteco.ploytechcourse.application.like;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.repository.DocumentRepository;
import org.plteco.ploytechcourse.domain.like.documentlike.service.DocumentLikeService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentLikeServiceApplication {

    private final DocumentLikeService documentLikeService;

    private final UserContextUtil userContextUtil;
    private final DocumentRepository documentRepository;

    private User getCurrentUser() {
        return userContextUtil.getCurrentUser();
    }

    private Document getDocument(long documentId){
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 글입니다.", HttpStatus.NOT_FOUND));
    }

    public void addLike(long documentId) {
        User user = getCurrentUser();
        Document document = getDocument(documentId);
        documentLikeService.addLike(document, user);
    }

    public void removeLike(long documentId) {
        User user = getCurrentUser();
        Document document = getDocument(documentId);
        documentLikeService.removeLike(document, user);
    }

}
