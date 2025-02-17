package org.plteco.ploytechcourse.domain.favorite.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {
    @EmbeddedId // 복합키
    private FavoriteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("documentId")
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Builder(builderMethodName = "favoriteBuilder")
    public Favorite(User user, Document document) {
        this.user = user;
        this.document = document;
        this.id = new FavoriteId(user.getId(), document.getId());
    }
}
