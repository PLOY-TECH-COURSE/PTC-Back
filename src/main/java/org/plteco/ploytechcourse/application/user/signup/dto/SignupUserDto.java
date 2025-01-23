package org.plteco.ploytechcourse.application.user.signup.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

/**
 * 사용자의 회원가입 정보를 담고 있는 DTO 클래스입니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupUserDto {

    @Schema(description = "사용자의 고유 ID", example = "hunon")
    @NotNull
    @Size(min = 4, max = 15)
    private String uid;

    @Schema(description = "사용자의 이름", example = "허온")
    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Schema(description = "사용자의 이메일 주소", example = "ploytechcourse@gmail.com")
    @NotNull
    @Email
    @Size(max = 320)
    private String email;

    @Schema(description = "이메일 인증 코드", example = "C1kdee")
    @NotNull
    private String code; // 인증 코드 (DB 체크 필요)

    @Schema(description = "사용자의 비밀번호", example = "ploytechcourse2025!!")
    @NotNull
    @Size(min = 10, max = 30)
    private String password;

    @Schema(description = "사용자가 입력한 비밀번호 확인", example = "ploytechcourse2025!!")
    @NotNull
    private String rePassword;

    @Schema(description = "사용자의 자기소개", example = "안녕하세요, 저는 개발자입니다.(필수 X)")
    private String bio;

    @Schema(description = "사용자의 학년", example = "1")
    @NotNull
    @Min(1)
    @Max(3)
    private Long grade;

    @Schema(description = "사용자의 반 번호", example = "4")
    @NotNull
    @Min(1)
    private Long userClass;

    @Schema(description = "사용자의 학번", example = "13")
    @NotNull
    @Min(1)
    private Long number;
}
