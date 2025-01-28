package org.plteco.ploytechcourse.domain.like.documentlike.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocumentLikeId{
    @Column(name = "document_id")
    private long documentId;

    @Column(name = "user_id")
    private long userId;
}
