package org.plteco.ploytechcourse.domain.announcement.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementUpdateRequestDTO;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementWriteRequestDTO;
import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.announcement.repository.AnnouncementRepository;
import org.plteco.ploytechcourse.domain.application.repository.StudentRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final StudentRepository studentRepository;

    @Override
    public Announcement writeDocument(User user, AnnouncementWriteRequestDTO writeRequest) {
        Announcement announcement = Announcement.from(user, writeRequest);
        return announcementRepository.save(announcement);
    }

    @Override
    public List<Announcement> getAnnouncements(Long start, Long end) {
        return announcementRepository.findWithPagination(start, end);
    }

    @Override
    public Announcement getAnnouncement(Long announcementId) {
        return announcementRepository.findById(announcementId).orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다."));
    }

    @Override
    public User getAnnouncementUser(Long announcementId) {
        return getAnnouncement(announcementId).getUser();
    }

    @Override
    public Optional<Long> getUserGeneration(Announcement announcement) {
        Long userId = announcement.getUser().getId();
        return studentRepository.findTechCourseIdByUserId(userId);
    }

    @Override
    public Announcement updateAnnouncement(AnnouncementUpdateRequestDTO updateRequest) {
        Announcement announcement = getAnnouncement(updateRequest.announcementId());

        Announcement newAnnouncement = Announcement.from(announcement, updateRequest);
        return announcementRepository.save(newAnnouncement);
    }

    @Override
    public void deleteAnnouncement(Long AnnouncementId) {
        announcementRepository.deleteById(AnnouncementId);
    }
}
