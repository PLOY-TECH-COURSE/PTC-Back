package org.plteco.ploytechcourse.application.document.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DocumentUpdateRequestDTO(
        @NotNull(message = "글 수정시 글 id는 필수 항목입니다.")
        @JsonProperty("document_id")
        Long documentId,

        @NotNull(message = "글 수정시 제목은 필수 항목입니다.")
        String title,

        @NotNull(message = "글 수정시 내용은 필수 항목입니다.")
        String content,

        String thumbnail,

        String introduction,

        @JsonProperty("hash_tag")
        List<String> hasTag
) {}
