package org.plteco.ploytechcourse.application.document.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DocumentWriteRequestDto {
    private String title;
    private String content;
    private String thumbnail;
    private String introduction;
    private Long categoryId;
    @Builder
    public DocumentWriteRequestDto(String title, String content, String thumbnail, String introduction, Long categoryId) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.introduction = introduction;
        this.categoryId = categoryId;
    }
}