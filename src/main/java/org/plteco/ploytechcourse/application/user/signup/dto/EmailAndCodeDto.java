package org.plteco.ploytechcourse.application.user.signup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 이메일과 인증 코드를 담고 있는 DTO 클래스입니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAndCodeDto {

    @Schema(description = "사용자의 이메일", example = "ploytechcourse@gmail.com")
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    @NotNull
    private String email;

    @Schema(description = "사용자에게 전송된 인증 코드", example = "C1ki1")
    @NotNull
    private String code;
}
