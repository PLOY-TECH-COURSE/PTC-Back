package org.plteco.ploytechcourse.application.favorite;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentsGetResponseDTO;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.HashTag;
import org.plteco.ploytechcourse.domain.document.repository.DocumentRepository;
import org.plteco.ploytechcourse.domain.document.service.Document_HashTagService;
import org.plteco.ploytechcourse.domain.favorite.service.FavoriteService;
import org.plteco.ploytechcourse.domain.like.documentlike.service.DocumentLikeService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceApplication {

    private final FavoriteService favoriteService;

    private final ModelMapper modelMapper;
    private final UserContextUtil userContextUtil;
    private final DocumentRepository documentRepository;
    private final DocumentLikeService documentLikeService;
    private final Document_HashTagService documentHashTagService;
    private User getCurrentUser() {
        return userContextUtil.getCurrentUser();
    }

    private Document getDocument(long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 글입니다.", HttpStatus.NOT_FOUND));
    }

    public void registerFavorite(long documentId) {
        User user = getCurrentUser();
        Document document = getDocument(documentId);
        favoriteService.registerFavorite(user, document);
    }

    public List<DocumentsGetResponseDTO> getFavoriteDocuments() {
        User user = getCurrentUser();
        return favoriteService.getFavoriteDocuments(user)
                .stream()
                .map(document ->
                        DocumentsGetResponseDTO.from(
                                document, documentHashTagService.getHashTagsForDocument(document).stream().map(HashTag::getName).toList(),
                                documentLikeService.getLikes(document.getId())))
                .toList();
    }

    public void deleteFavoriteByUser(long documentId) {
        User user = getCurrentUser();
        Document document = getDocument(documentId);
        favoriteService.deleteFavoriteByUser(user, document);
    }
}
