package org.plteco.ploytechcourse.application.user.permission.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.permission.service.PermissionService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePermissionApplicationImpl implements ChangePermissionApplication {

    private final PermissionService permissionService;

    @Override
    public void changePermissionApplication(Long id, RoleEnum role) {
        permissionService.changePermissions(id, role);
    }
}
