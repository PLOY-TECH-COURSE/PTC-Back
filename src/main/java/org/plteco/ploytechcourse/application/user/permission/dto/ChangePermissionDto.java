package org.plteco.ploytechcourse.application.user.permission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePermissionDto {
    private Long id;
    private RoleEnum role;
}
