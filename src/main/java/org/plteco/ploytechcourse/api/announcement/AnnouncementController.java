package org.plteco.ploytechcourse.api.announcement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementWriteRequestDTO;
import org.plteco.ploytechcourse.shared.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "announcment(공지사항 관련 api)", description = "공지사항 작성, 수정, 보기 등 공지사항과 관련된 API를 제공합니다.")
public class AnnouncementController {
    private final AnnouncmentService announcmentService;

    @Operation(summary = "공지사항 작성", description = "새로운 공지사항을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "공지사항 작성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(responseCode = "403", description = "권한 부족",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 형식의 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":400,\"message\":\"공지사항 작성시 제목은 필수 항목입니다.\",\"errorCode\":\"INVALID_ARGUMENT\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @PostMapping("/documents")
    public ResponseEntity<String> writeDocument(
            @RequestBody @Valid AnnouncementWriteRequestDTO announcementWriteRequestDTO
    ) {
        documentService.writeDocument(documentWriteRequestDto);

        return ResponseEntity.status(201).body("글쓰기에 성공하였습니다.");
    }
}
