package org.plteco.ploytechcourse.application.document.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record DocumentWriteRequestDTO(
        @NotNull(message = "글 작성시 제목은 필수 항목입니다.")
        String title,

        @NotNull(message = "글 작성시 내용은 필수 항목입니다.")
        String content,

        String thumbnail,

        String introduction,

        @JsonProperty("hash_tag")
        List<String> hasTag
) {}