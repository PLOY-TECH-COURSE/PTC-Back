package org.plteco.ploytechcourse.application.user.signup.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAndCodeDto {
    @Email
    private String email;
    private String code;
}
