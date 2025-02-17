package org.plteco.ploytechcourse.application.document.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DocumentSpreadDto {
    private Long id;
    private String thumbnail;
    private String title;
    private String description;
    private LocalDateTime createAt;
    private String userName;
    private String userProfile;
}
