package org.plteco.ploytechcourse.domain.announcement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "announcement_comment")
public class AnnouncementComment {

    @EmbeddedId
    private AnnouncementCommentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("announcementId")
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("commentId")
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Builder
    public AnnouncementComment(Announcement announcement, Comment comment) {
        this.id = new AnnouncementCommentId(announcement.getId(), comment.getId());
        this.announcement = announcement;
        this.comment = comment;
    }
}