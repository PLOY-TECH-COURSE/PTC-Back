package org.plteco.ploytechcourse.domain.favorite.repository;

import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.favorite.model.entity.Favorite;
import org.plteco.ploytechcourse.domain.favorite.model.entity.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId>{
    void deleteById(FavoriteId id);
    Optional<List<Favorite>> findById_UserId(long userId);
    void deleteById_Document_Id(long documentId);
    boolean existsById_DocumentId(long documentId);

}