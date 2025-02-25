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
        FavoriteId favoriteId = new FavoriteId(user.getId(), document.getId());

        if (favoriteRepository.existsById(favoriteId)) {
            throw new PltecoException("이미 즐겨찾기 한 문서입니다.", HttpStatus.BAD_REQUEST);
        }

        Favorite favorite = Favorite.favoriteBuilder()
                .user(user)
                .document(document)
                .build();

        return favoriteRepository.save(favorite);
    }

    public List<Document> getFavoriteDocuments(User user) {
        return favoriteRepository.findById_UserId(user.getId())
                .map(favorites -> favorites.stream()
                        .map(Favorite::getDocument)
                        .toList())
                .orElse(List.of());
    }

    public void deleteFavoriteByUser(User user, Document document) {
        FavoriteId favoriteId = new FavoriteId(user.getId(), document.getId());

        if (!favoriteRepository.existsById(favoriteId)) {
            throw new PltecoException("즐겨찾기 한 글이 아닙니다.", HttpStatus.BAD_REQUEST);
        }

        favoriteRepository.deleteById(favoriteId);
    }

    public void deleteFavoriteByDocument(Document document) {
        favoriteRepository.deleteByDocument(document);
    }

    public boolean isFavorite(User user, Document document) {
        if (user == null){
            return false;
        }
        FavoriteId favoriteId = new FavoriteId(user.getId(), document.getId());
        return favoriteRepository.existsById(favoriteId);
    }

}
