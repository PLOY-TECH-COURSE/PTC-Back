package org.plteco.ploytechcourse.application.announcement.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementWriteRequestDTO;
import org.plteco.ploytechcourse.domain.announcement.service.AnnouncementService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceApplication {
    private final UserContextUtil userContextUtil;

    private final AnnouncementService announcementService;

    public void writeAnnouncement(AnnouncementWriteRequestDTO writeRequest) {
        User user = userContextUtil.getCurrentUser();
        announcementService.writeDocument(user, writeRequest);
    }
}
