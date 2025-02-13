package org.plteco.ploytechcourse.application.favorite;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentSpreadDto;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.repository.DocumentRepository;
import org.plteco.ploytechcourse.domain.favorite.model.entity.Favorite;
import org.plteco.ploytechcourse.domain.favorite.repository.FavoriteRepository;
import org.plteco.ploytechcourse.domain.favorite.service.FavoriteService;
import org.plteco.ploytechcourse.domain.favorite.service.FavoriteServiceImpl;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceApplication {

    private final FavoriteServiceImpl favoriteService;

    private final ModelMapper modelMapper;
    private final UserContextUtil userContextUtil;
    private final DocumentRepository documentRepository;
    private final FavoriteRepository favoriteRepository;

    public void registerFavorite(long documentId) {
        User user = userContextUtil.getCurrentUser();

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 글입니다.", HttpStatus.NOT_FOUND));

        favoriteService.registerFavorite(user, document);
    }

    public List<DocumentSpreadDto> getFavoriteDocuments() {
        User user = userContextUtil.getCurrentUser();

        return favoriteService.getFavoriteDocuments(user)
                .stream()
                .map(document -> modelMapper.map(document, DocumentSpreadDto.class))
                .toList();
    }

    public void deleteFavoriteByUser(long documentId) {
        User user = userContextUtil.getCurrentUser();

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 글입니다.", HttpStatus.NOT_FOUND));

        favoriteService.deleteFavoriteByUser(user, document);
    }

    public void deleteFavoriteByDocumentId(long documentId) {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 글입니다.", HttpStatus.NOT_FOUND));

        favoriteService.deleteFavoriteByDocumentId(documentId);
    }

    public boolean isFavorite(long documentId) {
        User user = userContextUtil.getCurrentUser();

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 글입니다.", HttpStatus.NOT_FOUND));

        return favoriteService.isFavorite(user, document);
    }
}
