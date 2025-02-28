package org.plteco.ploytechcourse.api.like.documentlikecontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.like.DocumentLikeServiceApplication;
import org.plteco.ploytechcourse.shared.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/document-likes")
@RequiredArgsConstructor
@Tag(name = "DocumentLike-controller : 조재민", description = "글 좋아요 추가, 삭제 API")
public class DocumentLikeController {

    private final DocumentLikeServiceApplication documentLikeServiceApplication;

    @Operation(summary = "글 좋아요 추가", description = "특정 글 좋아요를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글 좋아요 추가 성공"),
            @ApiResponse(responseCode = "403", description = "권한 부족"),
            @ApiResponse(responseCode = "409", description = "좋아요가 이미 존재함",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":409,\"message\":\"이미 좋아요를 눌렀습니다.\",\"errorCode\":\"Conflict\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @PostMapping("/{document-id}")
    public ResponseEntity<Void> like(@PathVariable("document-id") long documentId) {
        documentLikeServiceApplication.addLike(documentId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "글 좋아요 삭제", description = "특정 글 좋아요를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "댓글 좋아요 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 부족"),
            @ApiResponse(responseCode = "404", description = "글이 존재하지 않음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":404,\"message\":\"존재하지 않는 글입니다.\",\"errorCode\":\"Not Found\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @DeleteMapping("/{document-id}")
    public ResponseEntity<Void> unlike(@PathVariable("document-id") long documentId) {
        documentLikeServiceApplication.removeLike(documentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
