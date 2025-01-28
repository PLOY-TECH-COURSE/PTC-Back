package org.plteco.ploytechcourse.domain.like.documentlike.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DocumentLike{
    @EmbeddedId
    private DocumentLikeId id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("documentId")
//    @JoinColumn(name = "document_id")
//    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
}
