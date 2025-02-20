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
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentUpdateRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentDetailGetResponseDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentsGetResponseDTO;
import org.plteco.ploytechcourse.application.document.service.DocumentServiceApplication;
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
            @ApiResponse(responseCode = "200", description = "글 작성 성공"),
            @ApiResponse(responseCode = "403", description = "권한 부족"),
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

        return ResponseEntity.status(200).body("글쓰기에 성공하였습니다.");
    }

    @Operation(summary = "글 작성", description = "새로운 글을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "글 작성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DocumentsGetResponseDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "403", description = "권한 부족"),
            @ApiResponse(responseCode = "400", description = "잘못된 형식의 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":400,\"message\":\"start 값이 올바르지 않습니다.\",\"errorCode\":\"INVALID_ARGUMENT\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @GetMapping("/documents")
    public ResponseEntity<List<DocumentsGetResponseDTO>> getDocument(
            @RequestParam("start") Long start
    ) {
        if(start < 0) throw new IllegalArgumentException("start(글 조회 시작점)가 잘못되었습니다.");

        List<DocumentsGetResponseDTO> result = documentService.getDocuments(start);

        return ResponseEntity.status(200).body(result);
    }

    @Operation(summary = "글 작성", description = "새로운 글을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "글 작성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DocumentsGetResponseDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "403", description = "권한 부족"),
            @ApiResponse(responseCode = "400", description = "잘못된 형식의 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":400,\"message\":\"start 값이 올바르지 않습니다.\",\"errorCode\":\"INVALID_ARGUMENT\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @GetMapping("/documents/search")
    public ResponseEntity<List<DocumentsGetResponseDTO>> searchDocument(
            @RequestParam("query") String query,
            @RequestParam("sort") String sortMethod,
            @RequestParam("start") int start
    ) {
        List<DocumentsGetResponseDTO> result = documentService.searchDocument(query, sortMethod, start);

        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/documents/{document-id}")
    public ResponseEntity<DocumentDetailGetResponseDTO> getDocumentDetail(
            @PathVariable("document-id") Long documentId
    ) {
        DocumentDetailGetResponseDTO result = documentService.getDocumentDetail(documentId);

        return ResponseEntity.status(200).body(result);
    }

    @PatchMapping("/documents")
    public ResponseEntity<String> updateDocument(
        @RequestBody @Valid DocumentUpdateRequestDTO documentUpdateRequestDto
    ) {
        documentService.updateDocument(documentUpdateRequestDto);

        return ResponseEntity.status(200).body("글 수정에 성공하였습니다.");
    }

    @DeleteMapping("/documents")
    public ResponseEntity<String> deleteDocument(
            @RequestParam("document_id") Long documentId
    ) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.status(200).body("글 삭제에 성공하였습니다.");
    }
}
