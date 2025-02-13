package org.plteco.ploytechcourse.domain.favorite.service;

import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.favorite.model.entity.Favorite;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.util.List;

public interface FavoriteService {
    Favorite registerFavorite(User user, Document document);
    List<Document> getFavoriteDocuments(User user);
    void deleteFavoriteByUser(User user, Document document);
    void deleteFavoriteByDocumentId(long documentId);
    boolean isFavorite(User user, Document document);
}
