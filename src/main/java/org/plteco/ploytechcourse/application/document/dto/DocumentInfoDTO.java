package org.plteco.ploytechcourse.application.document.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DocumentInfoDTO (
    @NotNull(message = "글 정보에 id(글 id)가 비어있습니다.")
    Long id,

    @NotNull(message = "글 정보에 title(글 제목)가 비어있습니다.")
    String title,

    @NotNull(message = "글 정보에 introduction(글 소개)가 비어있습니다.")
    String contents,

    @NotNull(message = "글 정보에 date(글 작성 날짜)가 비어있습니다.")
    LocalDate date
) {}