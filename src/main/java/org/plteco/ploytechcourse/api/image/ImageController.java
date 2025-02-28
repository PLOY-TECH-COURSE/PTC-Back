package org.plteco.ploytechcourse.api.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.image.dto.GCSRequest;
import org.plteco.ploytechcourse.application.image.dto.GCSResponse;
import org.plteco.ploytechcourse.application.image.service.GCSServiceApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Image-controller : 허동운")
public class ImageController {

    private final GCSServiceApplication gcsServiceApplication;

    @Operation(
            summary = "이미지 Url",
            description = "이미지를 저장하고 url로 반환함"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/S3")
    public ResponseEntity<GCSResponse> objectUpload(
            @Parameter(description = "이미지 올리는 객체", required = true)
            GCSRequest gcsRequest
    ) throws IOException {
        return ResponseEntity.ok(gcsServiceApplication.getGcsService(gcsRequest));
    }

}
