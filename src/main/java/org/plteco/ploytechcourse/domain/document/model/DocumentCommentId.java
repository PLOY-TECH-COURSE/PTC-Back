package org.plteco.ploytechcourse.domain.document.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocumentCommentId implements Serializable {
    @Column(name = "document_id", nullable = false)
    private Long documentId;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;
}
