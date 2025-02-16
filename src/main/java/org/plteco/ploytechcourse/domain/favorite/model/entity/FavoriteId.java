package org.plteco.ploytechcourse.domain.favorite.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.io.Serializable;


// 복합키 만드는 클래스입니다.
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FavoriteId implements Serializable {
    @Column(name = "user_id")
    long userId;

    @Column(name = "document_id")
    long documentId;
}
