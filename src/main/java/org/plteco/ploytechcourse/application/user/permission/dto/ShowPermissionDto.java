package org.plteco.ploytechcourse.application.user.permission.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowPermissionDto {

    @Schema(description = "사용자의 고유 아이디", example = "1")
    private Long id;

    @Schema(description = "사용자의 이름", example = "허온")
    private String name;

    @Schema(description = "사용자의 이메일", example = "huhon@huhon.com")
    private String email;

    @Schema(description = "사용자의 역할", example = "ROLE_ADMIN")
    private RoleEnum role;

}
