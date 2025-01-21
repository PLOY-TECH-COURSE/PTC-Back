package org.plteco.ploytechcourse.application.user.signup.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupUserDto {
    private String uid;
    private String name;
    private String email;
    private String code;
    private String password;
    private String rePassword;
    private String bio;
    private Long grade;
    private Long userClass;
    private Long number;
}
