package org.plteco.ploytechcourse.domain.favorite.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.io.Serializable;


// 복합키 만드는 클래스입니다.
@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FavoriteId implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;
}
