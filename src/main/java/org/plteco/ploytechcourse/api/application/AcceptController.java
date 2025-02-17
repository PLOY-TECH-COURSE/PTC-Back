package org.plteco.ploytechcourse.api.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.application.dto.AcceptApplicationDto;
import org.plteco.ploytechcourse.application.application.dto.ShowApplicationDto;
import org.plteco.ploytechcourse.application.application.service.AcceptApplication;
import org.plteco.ploytechcourse.application.user.permission.dto.ChangePermissionDto;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accept")
public class AcceptController {
    private final AcceptApplication acceptApplication;
    @Operation(
            summary = "신청 수락",
            description = "신청 수락함요",
            responses = {
                    @ApiResponse(responseCode = "200", description = "신청수락 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "서버 오류",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
            },
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @PostMapping
    public ResponseEntity<?> accept(
            @Valid
            @RequestBody
            AcceptApplicationDto acceptApplicationDto
    ) {
        acceptApplication.accept(acceptApplicationDto);
        throw new PltecoException("신청수락 성공", HttpStatus.OK);
    }
}
