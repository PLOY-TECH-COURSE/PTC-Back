package org.plteco.ploytechcourse.api.document;

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
import org.plteco.ploytechcourse.application.document.dto.request.DocumentUpdateRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentDetailGetResponseDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentsGetResponseDTO;
import org.plteco.ploytechcourse.application.document.service.DocumentServiceApplication;
import org.plteco.ploytechcourse.domain.document.model.SortMethod;
import org.plteco.ploytechcourse.shared.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "document(글 관련 api)", description = "글 작성, 수정, 보기 등 글과 관련된 API를 제공합니다.")
public class DocumentController {
    private final DocumentServiceApplication documentService;

    @Operation(summary = "글 작성", description = "새로운 글을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "글 작성 성공",
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
                            examples = @ExampleObject(value = "{\"status\":400,\"message\":\"글 작성시 제목은 필수 항목입니다.\",\"errorCode\":\"INVALID_ARGUMENT\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @PostMapping("/documents")
    public ResponseEntity<String> writeDocument(
            @RequestBody @Valid DocumentWriteRequestDTO documentWriteRequestDto
    ) {
        documentService.writeDocument(documentWriteRequestDto);

        return ResponseEntity.status(201).body("글쓰기에 성공하였습니다.");
    }

    @Operation(summary = "글 목록 조회", description = "start를 통해 갯수를 나눠 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "글 목록 가져오기 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DocumentsGetResponseDTO.class))
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
    @GetMapping("/documents")
    public ResponseEntity<List<DocumentsGetResponseDTO>> getDocuments(
            @RequestParam("start")
            @NotNull(message = "start 파라미터는 필수입니다.")
            @Min(value = 0, message = "start 값은 0 이상이어야 합니다.") Long start
    ) {
        List<DocumentsGetResponseDTO> result = documentService.getDocuments(start);

        return ResponseEntity.status(200).body(result);
    }

    @Operation(summary = "글 검색", description = "글 제목 또는 해시테그를 통해 검색어로 글들을 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "글 검색 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DocumentsGetResponseDTO.class))
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
    @GetMapping("/documents/search")
    public ResponseEntity<List<DocumentsGetResponseDTO>> searchDocument(
            @RequestParam("query")
            @NotNull(message = "query 파라미터는 필수입니다.") String query,

            @RequestParam("sort")
            @NotNull(message = "sort 파라미터는 필수입니다.") SortMethod sortMethod,

            @RequestParam("start")
            @NotNull(message = "start 파라미터는 필수입니다.")
            @Min(value = 0, message = "start 값은 0 이상이어야 합니다.") int start
    ) {
        List<DocumentsGetResponseDTO> result = documentService.searchDocument(query, sortMethod, start);

        return ResponseEntity.status(200).body(result);
    }

    @Operation(summary = "자세한 글 내용 조회", description = "글 id를 활용하여 글의 자세한 내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자세한 글 내용 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DocumentDetailGetResponseDTO.class)
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
                            examples = @ExampleObject(value = "{\"status\":400,\"message\":\"document-id 파라미터는 필수입니다.\",\"errorCode\":\"INVALID_ARGUMENT\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @GetMapping("/documents/{document-id}")
    public ResponseEntity<DocumentDetailGetResponseDTO> getDocumentDetail(
            @PathVariable("document-id")
            @NotNull(message = "document-id 파라미터는 필수입니다.")
            @Min(value = 1, message = "document-id 값은 1 이상이어야 합니다.") Long documentId
    ) {
        DocumentDetailGetResponseDTO result = documentService.getDocumentDetail(documentId);

        return ResponseEntity.status(200).body(result);
    }

    @Operation(summary = "글 수정", description = "글 내용을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "글 내용 수정 성공",
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
                            examples = @ExampleObject(value = "{\"status\":400,\"message\":\"글 작성시 제목은 필수 항목입니다.\",\"errorCode\":\"INVALID_ARGUMENT\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @PatchMapping("/documents")
    public ResponseEntity<String> updateDocument(
        @RequestBody @Valid DocumentUpdateRequestDTO documentUpdateRequestDto
    ) {
        documentService.updateDocument(documentUpdateRequestDto);

        return ResponseEntity.status(204).body("글 수정에 성공하였습니다.");
    }

    @Operation(summary = "글 삭제", description = "글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "글 삭제 성공",
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
                            examples = @ExampleObject(value = "{\"status\":400,\"message\":\"document-id 파라미터는 필수입니다.\",\"errorCode\":\"INVALID_ARGUMENT\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @DeleteMapping("/documents")
    public ResponseEntity<String> deleteDocument(
            @RequestParam("document_id")
            @NotNull(message = "document-id 파라미터는 필수입니다.")
            @Min(value = 1, message = "document-id 값은 1 이상이어야 합니다.") Long documentId
    ) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.status(204).body("글 삭제에 성공하였습니다.");
    }
}