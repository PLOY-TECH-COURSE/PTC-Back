package org.plteco.ploytechcourse.domain.user.signup.model.dto;

import lombok.Data;

@Data
public class User {
    private String ID;
    private String name;
    private String email;
    private String password;
    private String rePassword;
    private String phone;
    private String role;
    private Long grade;
    private Long userClass; // 변경: 'class' → 'userClass'
    private Long number;
}
