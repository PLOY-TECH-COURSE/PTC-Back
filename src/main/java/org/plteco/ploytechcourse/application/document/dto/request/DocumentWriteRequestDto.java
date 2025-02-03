package org.plteco.ploytechcourse.application.document.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DocumentWriteRequestDto(
        @NotNull(message = "글 작성시 제목은 필수 항목입니다.")
        String title,

        @NotNull(message = "글 작성시 내용은 필수 항목입니다.")
        String content,

        String thumbnail,

        String introduction,

        @JsonProperty("category")
        Integer categoryId
) {}