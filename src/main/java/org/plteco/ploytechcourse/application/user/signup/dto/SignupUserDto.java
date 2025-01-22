package org.plteco.ploytechcourse.application.user.signup.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupUserDto {

    @NotNull
    @Size(min = 4, max = 15)
    private String uid;

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @NotNull
    @Email
    @Size(max = 320)
    private String email;

    @NotNull
    private String code; // 인증 코드 (DB 체크 필요)

    @NotNull
    @Size(min = 10, max = 30)
    private String password;

    @NotNull
    private String rePassword;

    private String bio;

    @NotNull
    @Min(1)
    @Max(3)
    private Long grade;

    @NotNull
    @Min(1)
    private Long userClass;

    @NotNull
    @Min(1)
    private Long number;
}

