package org.plteco.ploytechcourse.domain.favorite.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.favorite.model.entity.Favorite;
import org.plteco.ploytechcourse.domain.favorite.model.entity.FavoriteId;
import org.plteco.ploytechcourse.domain.favorite.repository.FavoriteRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public Favorite registerFavorite(User user, Document document) {

        FavoriteId favoriteId = FavoriteId.builder()
                .user(user)
                .document(document)
                .build();

        if(favoriteRepository.findById(favoriteId).isPresent() && favoriteRepository.findById(favoriteId).get().getId().equals(favoriteId)) {
            throw new PltecoException("이미 즐겨찾기 한 문서입니다.", HttpStatus.BAD_REQUEST);
        }

        Favorite favorite = Favorite.builder()
                .id(favoriteId)
                .build();

        return favoriteRepository.save(favorite);
    }

    public List<Document> getFavoriteDocuments(User user) {
        return favoriteRepository.findById_UserId(user.getId())
                .map(favorites -> favorites.stream()
                        .map(favorite -> favorite.getId().getDocument())
                        .toList()
                )
                .orElse(List.of());  // null 대신 빈 리스트 반환
    }

    public void deleteFavoriteByUser(User user, Document document) {
        FavoriteId favoriteId = FavoriteId.builder()
                .user(user)
                .document(document)
                .build();

        if(favoriteRepository.findById(favoriteId).isEmpty() || !favoriteRepository.findById(favoriteId).get().getId().equals(favoriteId)) {
            throw new PltecoException("즐겨찾기 한 글이 아닙니다.", HttpStatus.BAD_REQUEST);
        }

        favoriteRepository.deleteById(favoriteId);
    }

    public void deleteFavoriteByDocumentId(long documentId) {

        if(favoriteRepository.existsById_DocumentId(documentId)){
            favoriteRepository.deleteById_Document_Id(documentId);
        }
    }

    public boolean isFavorite(User user, Document document) {
        FavoriteId favoriteId = FavoriteId.builder()
                .user(user)
                .document(document)
                .build();
        return favoriteRepository.existsById(favoriteId);
    }

}
