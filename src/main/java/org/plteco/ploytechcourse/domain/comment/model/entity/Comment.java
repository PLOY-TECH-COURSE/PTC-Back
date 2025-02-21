package org.plteco.ploytechcourse.domain.comment.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.like.commentlike.model.entity.CommentLike;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "comment", nullable = false, length = 500)
    private String comment;

    @Builder.Default
    @Column(name = "comment_like_count", nullable = false)
    private Long commentLikeCount = 0L;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> likes = new ArrayList<>();

    public void increaseLike() {
        this.commentLikeCount++;
    }

    public void decreaseLike() {
        if (this.commentLikeCount > 0)
            this.commentLikeCount--;
    }

    @PrePersist
    public void prePersist() {
        if (this.commentLikeCount == null) {
            this.commentLikeCount = 0L;
        }
    }

}
