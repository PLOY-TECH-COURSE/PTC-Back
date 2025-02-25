package org.plteco.ploytechcourse.domain.document.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "document_comment")
public class DocumentComment {
    @EmbeddedId
    private DocumentCommentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("documentId")
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("commentId")
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Builder
    public DocumentComment(Document document, Comment comment) {
        this.id = new DocumentCommentId(document.getId(), comment.getId());
        this.document = document;
        this.comment = comment;
    }
}

