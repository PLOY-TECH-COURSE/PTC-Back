package org.plteco.ploytechcourse.domain.announcement.service;

import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementWriteRequestDTO;
import org.plteco.ploytechcourse.domain.announcement.model.Announcement;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

public interface AnnouncementService {
    Announcement writeDocument(User user, AnnouncementWriteRequestDTO writeRequest);
}
