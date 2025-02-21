package org.plteco.ploytechcourse.domain.announcement.repository;

import org.plteco.ploytechcourse.domain.announcement.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Query(value = "SELECT * FROM announcement ORDER BY created_at DESC LIMIT :start, :size", nativeQuery = true)
    List<Announcement> findWithPagination(@Param("start") Long start, @Param("size") Long size);
}
