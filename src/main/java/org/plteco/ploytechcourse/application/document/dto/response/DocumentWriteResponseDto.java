package org.plteco.ploytechcourse.application.document.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DocumentWriteResponseDto {
    private String message;

    @Builder
    public DocumentWriteResponseDto(String message) {
        this.message = message;
    }
}