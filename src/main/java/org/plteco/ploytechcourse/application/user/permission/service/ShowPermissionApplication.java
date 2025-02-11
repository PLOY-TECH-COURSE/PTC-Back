package org.plteco.ploytechcourse.application.user.permission.service;

import org.plteco.ploytechcourse.application.user.permission.dto.ShowPermissionDto;

import java.util.List;

public interface ShowPermissionApplication {
    List<ShowPermissionDto> showPermissions();
}
