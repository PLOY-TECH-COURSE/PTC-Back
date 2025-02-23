package org.plteco.ploytechcourse.domain.like.commentlike.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommentLikeId implements Serializable {
    @Column(name = "comment_id")
    private long commentId;

    @Column(name = "user_id")
    private Long userId;
}
