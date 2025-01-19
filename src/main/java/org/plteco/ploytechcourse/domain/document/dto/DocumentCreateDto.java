package org.plteco.ploytechcourse.domain.document.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.plteco.ploytechcourse.domain.document.model.Document;

import java.time.LocalDate;

public class DocumentCreateDto {
    @Getter
    @Setter
    public class Request {
        private String title;
        private String content;
        private String thumbnail;
        private String introduction;
        private Long categoryId;
        @Builder
        public Request(String title, String content, String thumbnail, String introduction, Long categoryId) {
            this.title = title;
            this.content = content;
            this.thumbnail = thumbnail;
            this.introduction = introduction;
            this.categoryId = categoryId;
        }
    }
    @Getter
    @Setter
    public class Response {
        private String message;

        @Builder
        public Response(String message) {
            this.message = message;
        }
    }

    public Document RequestToDocument(DocumentCreateDto.Request request) {
        return Document.builder()
                .userId()
                .title()
                .content()
                .thumbnail()
                .introduction()
                .createAt()
                .categoryId()
                .build();
    }
}
