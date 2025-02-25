package org.plteco.ploytechcourse.domain.comment.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.announcement.model.entity.AnnouncementComment;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.DocumentComment;
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

    @Column(name = "comment", nullable = false, length = 500)
    private String comment;

    @Builder.Default
    @Column(name = "comment_like_count", nullable = false)
    private Long commentLikeCount = 0L;

    @Builder.Default
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> likes = new ArrayList<>();

    @OneToOne(mappedBy = "comment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private DocumentComment documentComment;

    @OneToOne(mappedBy = "comment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private AnnouncementComment announcementComment;


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
