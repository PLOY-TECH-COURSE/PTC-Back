package org.plteco.ploytechcourse.domain.like.commentlike.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

@Entity
@NoArgsConstructor
@Getter
public class CommentLike{

    @EmbeddedId
    private CommentLikeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("commentId")
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public CommentLike(Comment comment, User user) {
        this.id = new CommentLikeId(comment.getId(), user.getId()); // ID 직접 생성
        this.comment = comment;
        this.user = user;
    }
}
