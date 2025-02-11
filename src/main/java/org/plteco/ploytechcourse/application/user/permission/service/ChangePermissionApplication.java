package org.plteco.ploytechcourse.application.user.permission.service;


import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;

public interface ChangePermissionApplication {
    void changePermissionApplication(Long id, RoleEnum role);
}
