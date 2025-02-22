package org.plteco.ploytechcourse.application.announcement.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.plteco.ploytechcourse.application.announcement.dto.AnnouncementInfoDTO;
import org.plteco.ploytechcourse.application.announcement.dto.AnnouncementUserInfoDTO;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementUpdateRequestDTO;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementWriteRequestDTO;
import org.plteco.ploytechcourse.application.announcement.dto.response.AnnouncementDetailGetResponseDTO;
import org.plteco.ploytechcourse.application.announcement.dto.response.AnnouncementGetResponseDTO;
import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.announcement.service.AnnouncementService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceApplication {
    private final UserContextUtil userContextUtil;
    private final ModelMapper modelMapper;

    private final AnnouncementService announcementService;

    private AnnouncementGetResponseDTO mapToAnnouncementsResponseDTO(Announcement announcement) {
        return AnnouncementGetResponseDTO.from(announcement);
    }

    public void writeAnnouncement(AnnouncementWriteRequestDTO writeRequest) {
        User user = userContextUtil.getCurrentUser();
        announcementService.writeDocument(user, writeRequest);
    }

    public List<AnnouncementGetResponseDTO> getAnnouncements(Long start) {
        List<Announcement> announcements = announcementService.getAnnouncements(start, start + 20);

        return announcements.stream()
                .map(this::mapToAnnouncementsResponseDTO)
                .toList();
    }

    public AnnouncementDetailGetResponseDTO getAnnouncementDetail(Long announcementId) {

        Announcement announcement =  announcementService.getAnnouncement(announcementId);
        AnnouncementInfoDTO announcementInfo = modelMapper.map(announcement, AnnouncementInfoDTO.class);

        User writer = announcementService.getAnnouncementUser(announcementId);
        AnnouncementUserInfoDTO userInfo = modelMapper.map(writer, AnnouncementUserInfoDTO.class);

        Long generation = announcementService.getUserGeneration(announcement).orElse(null);

        return new AnnouncementDetailGetResponseDTO(announcementInfo, userInfo, generation);
    }

    public void updateAnnouncement(AnnouncementUpdateRequestDTO announcementUpdateRequestDTO) {
        announcementService.updateAnnouncement(announcementUpdateRequestDTO);
    }

    public void deleteAnnouncement(Long AnnouncementId) {
        announcementService.deleteAnnouncement(AnnouncementId);
    }
}
