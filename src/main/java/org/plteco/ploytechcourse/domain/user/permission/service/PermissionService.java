package org.plteco.ploytechcourse.domain.user.permission.service;

import org.plteco.ploytechcourse.application.application.dto.ShowApplicationDto;
import org.plteco.ploytechcourse.application.user.permission.dto.ShowPermissionDto;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;

import java.util.List;

public interface PermissionService {
    List<ShowPermissionDto> showPermissions();
    void changePermissions(Long id, RoleEnum role);
}
