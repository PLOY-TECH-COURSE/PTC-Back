package org.plteco.ploytechcourse.domain.like.documentlike.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor // JPA를 위한 기본 생성자
@AllArgsConstructor
@EqualsAndHashCode
public class DocumentLikeId implements Serializable {  // EmbeddedId는 Serializable 필수
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "user_id")
    private Long userId;
}
