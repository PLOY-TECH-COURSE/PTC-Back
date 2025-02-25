package org.plteco.ploytechcourse.domain.announcement.repository;

import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.announcement.model.entity.AnnouncementComment;
import org.plteco.ploytechcourse.domain.announcement.model.entity.AnnouncementCommentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementCommentRepository extends JpaRepository<AnnouncementComment, AnnouncementCommentId> {
    List<AnnouncementComment> findCommentsByAnnouncement(Announcement announcement);
}
