package org.plteco.ploytechcourse.api.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.application.dto.AcceptApplicationDto;
import org.plteco.ploytechcourse.application.application.dto.DeclineApplicationRequestDTO;
import org.plteco.ploytechcourse.application.application.service.AcceptApplication;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/decline")
public class DeclineController {
    private final AcceptApplication acceptApplication;
    @Operation(
            summary = "신청거절",
            description = "신청 수락함요",
            responses = {
                    @ApiResponse(responseCode = "200", description = "신청 거절 성공"),
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
    public ResponseEntity<?> decline(
            @RequestBody
            DeclineApplicationRequestDTO dto
    ) {
        System.out.println("dto.getId() = " + dto.getId());
        acceptApplication.decline(dto.getId());
        throw new PltecoException("거절 성공", HttpStatus.OK);
    }
}
