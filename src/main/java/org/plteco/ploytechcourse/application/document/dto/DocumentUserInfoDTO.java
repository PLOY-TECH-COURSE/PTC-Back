package org.plteco.ploytechcourse.application.document.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentUserInfoDTO {
    private Long id;
    private String uid;
    private String profile;
}