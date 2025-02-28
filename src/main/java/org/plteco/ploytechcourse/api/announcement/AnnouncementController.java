package org.plteco.ploytechcourse.api.announcement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementUpdateRequestDTO;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementWriteRequestDTO;
import org.plteco.ploytechcourse.application.announcement.dto.response.AnnouncementDetailGetResponseDTO;
import org.plteco.ploytechcourse.application.announcement.dto.response.AnnouncementGetResponseDTO;
import org.plteco.ploytechcourse.application.announcement.service.AnnouncementServiceApplication;
import org.plteco.ploytechcourse.shared.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Announcement-controller(공지사항 관련 api) : 허온", description = "공지사항 작성, 수정, 보기 등 공지사항과 관련된 API를 제공합니다.")
public class AnnouncementController {
    private final AnnouncementServiceApplication announcementServiceApplication;

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
    @PostMapping("/announcements")
    public ResponseEntity<String> writeDocument(
            @RequestBody @Valid AnnouncementWriteRequestDTO request
    ) {
        announcementServiceApplication.writeAnnouncement(request);

        return ResponseEntity.status(201).body("공지사항 작성에 성공하였습니다.");
    }

    @Operation(summary = "공지사항 목록 조회", description = "start를 통해 갯수를 나눠 공지사항을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 목록 가져오기 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AnnouncementGetResponseDTO.class))
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
                            examples = @ExampleObject(value = "{\"status\":400,\"message\":\"start 파라미터는 필수입니다.\",\"errorCode\":\"INVALID_ARGUMENT\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @GetMapping("/announcements")
    public ResponseEntity<List<AnnouncementGetResponseDTO>> getAnnouncements(
            @RequestParam("start")
            @NotNull(message = "start 파라미터는 필수입니다.")
            @Min(value = 0, message = "start 값은 0 이상이어야 합니다.") Long start
    ) {
        List<AnnouncementGetResponseDTO> result = announcementServiceApplication.getAnnouncements(start);

        return ResponseEntity.status(200).body(result);
    }

    @Operation(summary = "자세한 공지사항 내용 조회", description = "공지사항 id를 활용하여 글의 자세한 내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자세한 공지사항 내용 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnnouncementDetailGetResponseDTO.class)
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
                            examples = @ExampleObject(value = "{\"status\":400,\"message\":\"announcement-id 파라미터는 필수입니다.\",\"errorCode\":\"INVALID_ARGUMENT\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @GetMapping("/announcements/{announcement-id}")
    public ResponseEntity<AnnouncementDetailGetResponseDTO> getAnnouncementDetail(
            @PathVariable("announcement-id")
            @NotNull(message = "announcement-id 파라미터는 필수입니다.")
            @Min(value = 1, message = "announcement-id 값은 1 이상이어야 합니다.") Long announcementId
    ) {
        AnnouncementDetailGetResponseDTO result = announcementServiceApplication.getAnnouncementDetail(announcementId);

        return ResponseEntity.status(200).body(result);
    }

    @Operation(summary = "공지사항 수정", description = "공지사항 내용을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "공지사항 내용 수정 성공",
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
    @PatchMapping("/announcements")
    public ResponseEntity<String> updateDocument(
            @RequestBody @Valid AnnouncementUpdateRequestDTO announcementUpdateRequestDTO
    ) {
        announcementServiceApplication.updateAnnouncement(announcementUpdateRequestDTO);

        return ResponseEntity.status(204).body("글 수정에 성공하였습니다.");
    }

    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "공지사항 삭제 성공",
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
                            examples = @ExampleObject(value = "{\"status\":400,\"message\":\"announcement-id 파라미터는 필수입니다.\",\"errorCode\":\"INVALID_ARGUMENT\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @DeleteMapping("/announcements")
    public ResponseEntity<String> deleteDocument(
            @RequestParam("announcement_id")
            @NotNull(message = "announcement-id 파라미터는 필수입니다.")
            @Min(value = 1, message = "announcement-id 값은 1 이상이어야 합니다.") Long announcementId
    ) {
        announcementServiceApplication.deleteAnnouncement(announcementId);
        return ResponseEntity.status(204).body("글 삭제에 성공하였습니다.");
    }
}
