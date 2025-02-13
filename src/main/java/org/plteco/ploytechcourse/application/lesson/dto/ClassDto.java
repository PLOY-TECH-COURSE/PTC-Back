package org.plteco.ploytechcourse.application.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDto {
    private Long trackId;
    private String name;
    private String content;
    private String thumbnail;
    private String explanation;
}
