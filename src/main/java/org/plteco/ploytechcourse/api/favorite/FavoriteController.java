package org.plteco.ploytechcourse.api.favorite;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentSpreadDto;
import org.plteco.ploytechcourse.application.favorite.FavoriteServiceApplication;
import org.plteco.ploytechcourse.shared.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "favorite-controller", description = "favorite 관련 API")
@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteServiceApplication favoriteServiceApplication;


    @Operation(summary = "즐겨찾기 추가", description = "특정 문서를 즐겨찾기에 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "즐겨찾기 추가 성공"),
            @ApiResponse(responseCode = "400", description = "이미 즐겨찾기한 문서",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":400,\"message\":\"이미 즐겨찾기한 문서입니다.\",\"errorCode\":\"BAD_REQUEST\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
            @ApiResponse(responseCode = "404", description = "글이 존재하지 않음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":404,\"message\":\"존재하지 않는 글입니다.\",\"errorCode\":\"Not Found\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @PostMapping("/favorite/{document-id}")
    public ResponseEntity<Void> registerFavorite(
            @Parameter(description = "글 아이디", required = true)
            @PathVariable("document-id") long documentId) {
        favoriteServiceApplication.registerFavorite(documentId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "즐겨찾기 조회", description = "자신이 즐겨찾기한 글을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즐겨찾기된 글 조회 성공"),
    })
    @GetMapping("/favorite")
    public ResponseEntity<List<DocumentSpreadDto>> getFavoriteDocuments() {
        List<DocumentSpreadDto> documentSpreadDtos = favoriteServiceApplication.getFavoriteDocuments();
        return new ResponseEntity<>(documentSpreadDtos, HttpStatus.OK);
    }

    @Operation(summary = "즐겨찾기 삭제", description = "특정 문서를 즐겨찾기에서 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "즐겨찾기 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "즐겨찾기 하지 않음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":400,\"message\":\"즐겨찾기한 글이 아닙니다.\",\"errorCode\":\"BAD_REQUEST\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
            @ApiResponse(responseCode = "404", description = "글이 존재하지 않음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":404,\"message\":\"존재하지 않는 글입니다.\",\"errorCode\":\"Not Found\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @DeleteMapping("/favorite/{document-id}")
    public ResponseEntity<Void> deleteFavorite(
            @Parameter(description = "글 아이디", required = true)
            @PathVariable("document-id") long documentId) {
        favoriteServiceApplication.deleteFavoriteByUser(documentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
