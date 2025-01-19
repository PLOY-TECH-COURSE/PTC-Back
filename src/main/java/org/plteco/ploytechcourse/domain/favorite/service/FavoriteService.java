package org.plteco.ploytechcourse.domain.favorite.service;

import org.plteco.ploytechcourse.domain.favorite.model.entity.Favorite;

public interface FavoriteService {
    public Favorite registerFavorite(long user_id, long document_id);
    // public Optional<List<Document>> getFavorite(long userId, long documentId);
    public void deleteFavorite(long user_id, long document_id);
}
