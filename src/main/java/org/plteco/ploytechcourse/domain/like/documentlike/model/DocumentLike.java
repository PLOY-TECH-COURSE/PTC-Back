package org.plteco.ploytechcourse.domain.like.documentlike.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.io.Serializable;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DocumentLike{
    @EmbeddedId
    private DocumentLikeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("documentId")
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public DocumentLike(Document document, User user) {
        this.id = new DocumentLikeId(document.getId(), user.getId()); // ID 직접 생성
        this.document = document;
        this.user = user;
    }
}
