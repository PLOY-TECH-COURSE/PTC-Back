package org.plteco.ploytechcourse.application.announcement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.plteco.ploytechcourse.application.announcement.dto.AnnouncementInfoDTO;
import org.plteco.ploytechcourse.application.announcement.dto.AnnouncementUserInfoDTO;

public record AnnouncementDetailGetResponseDTO(
        @NotNull(message = "글 자세히 보기에 document(글 정보)가 비어있습니다.")
        @JsonProperty("document")
        AnnouncementInfoDTO documentInfoDTO,

        @NotNull(message = "글 자세히 보기에 user_id(작성자 id)이 비어있습니다.")
        AnnouncementUserInfoDTO userInfoDTO,

        String generation
) {}