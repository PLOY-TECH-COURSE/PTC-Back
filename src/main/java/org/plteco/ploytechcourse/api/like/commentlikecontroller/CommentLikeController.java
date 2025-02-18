package org.plteco.ploytechcourse.api.like.commentlikecontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.like.CommentLikeServiceApplication;
import org.plteco.ploytechcourse.domain.like.commentlike.service.CommentLikeService;
import org.plteco.ploytechcourse.shared.exception.ErrorResponse;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "commentLike-controller", description = "댓글 좋아요 추가, 삭제 API")
@RestController
@RequestMapping("/comment-likes")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeServiceApplication commentLikeService;

    @Operation(summary = "댓글 좋아요 추가", description = "특정 댓글 좋아요를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글 좋아요 추가 성공"),
            @ApiResponse(responseCode = "403", description = "권한 부족"),
            @ApiResponse(responseCode = "409", description = "좋아요가 이미 존재함",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":409,\"message\":\"이미 좋아요를 눌렀습니다.\",\"errorCode\":\"Conflict\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @PostMapping("/{comment-id}")
    public ResponseEntity<Void> like(
            @Parameter(description = "댓글 아이디", required = true)
            @PathVariable("comment-id") long commentId) {
        commentLikeService.addLike(commentId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "댓글 좋아요 삭제", description = "특정 댓글 좋아요를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "댓글 좋아요 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 부족"),
            @ApiResponse(responseCode = "404", description = "댓글이 존재하지 않음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":404,\"message\":\"존재하지 않는 댓글입니다.\",\"errorCode\":\"Not Found\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
    })
    @DeleteMapping("/{comment-id}")
    public ResponseEntity<Void> unlike(
            @Parameter(description = "댓글 아이디", required = true)
            @PathVariable("comment-id") long commentId) {
        commentLikeService.unLike(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
