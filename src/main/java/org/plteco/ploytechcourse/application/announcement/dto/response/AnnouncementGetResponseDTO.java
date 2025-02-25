package org.plteco.ploytechcourse.application.announcement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;

import java.time.LocalDate;

public record AnnouncementGetResponseDTO(
        @NotNull(message = "공지사항 목록 조회에 announcement_id(공지사항 id)가 비어있습니다.")
        @JsonProperty("announcements_id")
        Long announcementId,

        @NotNull(message = "공지사항 목록 조회에 title(제목)이 비어있습니다.")
        String title,

        String introduction,

        @NotNull(message = "공지사항 목록 조회에 thumbnail(썸네일)이 비어있습니다.")
        String thumbnail,

        @NotNull(message = "공지사항 목록 조회에 user_id(사용자 id)가 비어있습니다.")
        @JsonProperty("user_id")
        Long userId,

        @NotNull(message = "공지사항 목록 조회에 name(사용자 아이디)이 비어있습니다.")
        String name,

        @NotNull(message = "공지사항 목록 조회에 date(날짜)가 비어있습니다.")
        LocalDate date
) {
    public static AnnouncementGetResponseDTO from(Announcement announcement) {
        return new AnnouncementGetResponseDTO(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getIntroduction(),
                announcement.getThumbnail(),
                announcement.getUser().getId(),
                announcement.getUser().getName(),
                announcement.getCreateAt()
        );
    }
}