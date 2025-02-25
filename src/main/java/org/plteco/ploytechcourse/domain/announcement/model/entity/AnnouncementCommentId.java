package org.plteco.ploytechcourse.domain.announcement.model.entity;

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
public class AnnouncementCommentId implements Serializable {
    @Column(name = "announcement_id", nullable = false)
    private Long announcementId;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;
}
