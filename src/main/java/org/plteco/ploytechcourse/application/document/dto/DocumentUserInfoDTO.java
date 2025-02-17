package org.plteco.ploytechcourse.application.document.dto;

import jakarta.validation.constraints.NotNull;

public record DocumentUserInfoDTO(
        @NotNull(message = "작성자 정보에 id(작성자 id)가 비어있습니다.")
        Long id,

        @NotNull(message = "작성자 정보에 name(작성자 이름)이 비어있습니다.")
        String name,

        @NotNull(message = "작성자 정보에 profile(작성자 프로필)이 비어있습니다.")
        String profile
) {}
