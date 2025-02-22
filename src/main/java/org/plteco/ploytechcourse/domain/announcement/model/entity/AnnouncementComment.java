package org.plteco.ploytechcourse.domain.announcement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "announcement_comment")
public class AnnouncementComment {

    @EmbeddedId
    private AnnouncementCommentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("announcementId")
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("commentId")
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;
}