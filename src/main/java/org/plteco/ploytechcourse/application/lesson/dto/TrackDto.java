package org.plteco.ploytechcourse.application.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackDto {
    private Long generation;
    private String name;
    private String explanation;
}
