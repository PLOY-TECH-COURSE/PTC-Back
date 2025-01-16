package org.plteco.ploytechcourse.application.user.signup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupUserDto {
    //이거 첫번째, 맨앞 두글자가 모두 대문자 인 경우 이어진 대문자를 모두 소문자로 변경하는 것
    //두번째, 위에 경우가 아닐 때는 맨 앞글자만 소문자로 바꿔주는 것
    //규칙때문에 정의 해놓는겁니다.
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
