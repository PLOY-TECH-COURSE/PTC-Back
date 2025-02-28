package org.plteco.ploytechcourse.api.user.permission;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.application.service.ApplyApplicationApplication;
import org.plteco.ploytechcourse.application.user.permission.dto.ChangePermissionDto;
import org.plteco.ploytechcourse.application.user.permission.dto.ShowPermissionDto;
import org.plteco.ploytechcourse.application.user.permission.service.ChangePermissionApplication;
import org.plteco.ploytechcourse.application.user.permission.service.ShowPermissionApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
@Tag(name = "Permissions-controller : 허동운")
public class PermissionController {
    private final ChangePermissionApplication changePermissionApplication;
    private final ShowPermissionApplication showPermissionApplication;

    @GetMapping
    public List<ShowPermissionDto> showPermissions() {
        return showPermissionApplication.showPermissions();
    }

    @PatchMapping
    public void changePermission(
            @RequestBody ChangePermissionDto changePermissionDto
            ){
        changePermissionApplication.changePermissionApplication(
                changePermissionDto.getId(), changePermissionDto.getRole()
        );
    }

}
