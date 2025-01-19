package org.plteco.ploytechcourse.domain.favorite.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.favorite.model.entity.Favorite;
import org.plteco.ploytechcourse.domain.favorite.model.entity.FavoriteId;
import org.plteco.ploytechcourse.domain.favorite.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public Favorite registerFavorite(long user_id, long document_id) {

        FavoriteId favoriteId = FavoriteId.builder()
                .user_id(user_id)
                .document_id(document_id)
                .build();

        // favorite의 id를 favoriteId로 설정
        Favorite favorite = Favorite.builder()
                .id(favoriteId)
                .build();

        return favoriteRepository.save(favorite);
    }

//    public Optional<List<Document>> getFavorite(long user_id) {
//
//    }

    public void deleteFavorite(long user_id, long document_id) {
        FavoriteId favoriteId = FavoriteId.builder()
                .user_id(user_id)
                .document_id(document_id)
                .build();

        favoriteRepository.deleteById(favoriteId);
    }
}
