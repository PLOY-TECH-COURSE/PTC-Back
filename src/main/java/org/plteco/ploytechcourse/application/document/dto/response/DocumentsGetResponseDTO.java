package org.plteco.ploytechcourse.application.document.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DocumentsGetResponseDTO(
        @NotNull(message = "글 목록 조회에 document_id(글 id)가 비어있습니다.")
        @JsonProperty("documents_id")
        Long documentId,

        @NotNull(message = "글 목록 조회에 title(제목)이 비어있습니다.")
        String title,

        String introduction,

        @NotNull(message = "글 목록 조회에 thumbnail(썸네일)이 비어있습니다.")
        String thumbnail,

        @NotNull(message = "글 목록 조회에 likes(좋아요)가 비어있습니다.")
        Long likes,

        @NotNull(message = "글 목록 조회에 user_id(사용자 id)가 비어있습니다.")
        @JsonProperty("user_id")
        Long userId,

        @NotNull(message = "글 목록 조회에 name(사용자 이름)이 비어있습니다.")
        String name,

        @NotNull(message = "글 목록 조회에 profile(사용자 프로필)이 비어있습니다.")
        String profile,

        @NotNull(message = "글 목록 조회에 date(날짜)가 비어있습니다.")
        LocalDate date
) {}
