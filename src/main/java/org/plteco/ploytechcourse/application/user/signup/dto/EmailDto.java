package org.plteco.ploytechcourse.application.user.signup.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDto {
    private String email;
}
