package org.plteco.ploytechcourse.domain.announcement.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementWriteRequestDTO;
import org.plteco.ploytechcourse.domain.announcement.model.Announcement;
import org.plteco.ploytechcourse.domain.announcement.repository.AnnouncementRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    @Override
    public Announcement writeDocument(User user, AnnouncementWriteRequestDTO writeRequest) {
        Announcement announcement = Announcement.from(user, writeRequest);
        return announcementRepository.save(announcement);
    }
}
