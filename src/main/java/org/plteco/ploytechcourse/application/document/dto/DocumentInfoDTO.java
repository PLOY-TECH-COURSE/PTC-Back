package org.plteco.ploytechcourse.application.document.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentInfoDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDate createAt;
    private String thumbnail;
    private String introduction;
}