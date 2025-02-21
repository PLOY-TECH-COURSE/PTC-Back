package org.plteco.ploytechcourse.application.announcement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AnnouncementWriteRequestDTO(
        @NotNull(message = "공지사항 작성시 제목은 필수 항목입니다.")
        String title,

        @NotNull(message = "공지사항 작성시 내용은 필수 항목입니다.")
        String content,

        String thumbnail,

        String introduction
) {}