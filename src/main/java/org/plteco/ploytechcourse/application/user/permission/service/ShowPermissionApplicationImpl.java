package org.plteco.ploytechcourse.application.user.permission.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.permission.dto.ShowPermissionDto;
import org.plteco.ploytechcourse.domain.user.permission.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowPermissionApplicationImpl implements ShowPermissionApplication {

    private final PermissionService permissionService;

    @Override
    public List<ShowPermissionDto> showPermissions() {
        return permissionService.showPermissions();
    }
}
