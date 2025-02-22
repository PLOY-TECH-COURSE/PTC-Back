package org.plteco.ploytechcourse.application.announcement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnnouncementInfoDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDate createAt;
}