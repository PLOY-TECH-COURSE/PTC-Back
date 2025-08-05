package org.plteco.ploytechcourse.domain.announcement.service;

import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementUpdateRequestDTO;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementWriteRequestDTO;
import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    Announcement writeDocument(User user, AnnouncementWriteRequestDTO writeRequest);
    List<Announcement> getAnnouncements(Long start, Long end);
    Announcement getAnnouncement(Long announcementId);
    User getAnnouncementUser(Long announcementId);
    Optional<Integer> getUserGeneration(Announcement announcement);
    Announcement updateAnnouncement(AnnouncementUpdateRequestDTO announcementUpdateRequestDTO);
    void deleteAnnouncement(Long announcementId);
}
