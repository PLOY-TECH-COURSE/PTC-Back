package org.plteco.ploytechcourse.domain.announcement.repository;

import org.plteco.ploytechcourse.domain.announcement.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
