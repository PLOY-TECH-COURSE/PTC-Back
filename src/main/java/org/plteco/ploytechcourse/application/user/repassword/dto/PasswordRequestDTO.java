package org.plteco.ploytechcourse.application.user.repassword.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PasswordRequestDTO {
    private String email;
    private String password;
}
