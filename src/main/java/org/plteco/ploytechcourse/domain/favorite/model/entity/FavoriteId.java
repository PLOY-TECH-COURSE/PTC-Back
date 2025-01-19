package org.plteco.ploytechcourse.domain.favorite.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


// 복합키 만드는 클래스입니다.
@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FavoriteId implements Serializable {
    @Column(name = "user_id")
    private long user_id;

    @Column(name = "document_id")
    private long document_id;
}
