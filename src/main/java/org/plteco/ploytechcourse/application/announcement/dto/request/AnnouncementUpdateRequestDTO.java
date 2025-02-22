package org.plteco.ploytechcourse.application.announcement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record AnnouncementUpdateRequestDTO(
        @NotNull(message = "공지사항 수정시 글 id는 필수 항목입니다.")
        @JsonProperty("announcement_id")
        Long announcementId,

        @NotNull(message = "공지사항 수정시 제목은 필수 항목입니다.")
        String title,

        @NotNull(message = "공지사항 수정시 내용은 필수 항목입니다.")
        String content,

        String thumbnail,

        String introduction
) {}