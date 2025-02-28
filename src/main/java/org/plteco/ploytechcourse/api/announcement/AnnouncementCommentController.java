package org.plteco.ploytechcourse.api.announcement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.announcement.service.AnnouncementCommentServiceApplication;
import org.plteco.ploytechcourse.application.comment.dto.CommentDTO;
import org.plteco.ploytechcourse.application.comment.dto.RequestCommentDTO;
import org.plteco.ploytechcourse.shared.exception.ErrorResponse;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcement")
@Tag(name = "AnnouncementComment-controller : 조재민", description = "공지 댓글 작성, 수정, 보기 API를 제공합니다.")
@RequiredArgsConstructor
public class AnnouncementCommentController {

    private final AnnouncementCommentServiceApplication announcementCommentServiceApplication;

    /**
     * 댓글 등록 (POST)
     * 주어진 문서(document-id)에 대해 요청 본문에 포함된 댓글 데이터를 사용하여 댓글을 등록합니다.
     *
     * @param announcementId  댓글이 등록될 문서의 고유 아이디.
     * @param commentData 요청 본문으로 전달되는 댓글 DTO.
     * @return 댓글 생성 성공 시 HTTP 상태 코드 201 (Created)을 반환합니다.
     * @throws PltecoException 요청 데이터가 잘못되었거나, 글이 존재하지 않는 경우 예외가 발생합니다.
     */
    @Operation(summary = "댓글 등록", description = "특정 문서에 댓글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 - 댓글 내용 누락 또는 500자 초과 등",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(value = "[{\"status\":400,\"message\":\"댓글 내용이 작성되지 않았습니다.\",\"errorCode\":\"INVALID_ARGUMENT\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}," +
                                            "{\"status\":400,\"message\":\"댓글은 500자를 초과할 수 없습니다.\",\"errorCode\":\"INVALID_ARGUMENT\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}]")})),
            @ApiResponse(responseCode = "404", description = "글이 존재하지 않음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":404,\"message\":\"존재하지 않는 글입니다.\",\"errorCode\":\"Not Found\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping("/comments/{announcement-id}")
    public ResponseEntity<Void> createComment(
            @Parameter(description = "공지 글 아이디", required = true)
            @PathVariable("announcement-id") Long announcementId,
            @Valid @RequestBody RequestCommentDTO commentData) {

        String commentText = commentData.getCommentText();
        announcementCommentServiceApplication.createComment(announcementId, commentText);
        return new ResponseEntity<>(HttpStatus.CREATED);  // 성공적으로 생성된 경우 201 반환
    }

    /**
     * 댓글 조회 (GET)
     * 주어진 문서(document-id)에 등록된 댓글 목록을 조회합니다.
     *
     * @param announcementId 댓글 목록을 조회할 문서의 고유 아이디.
     * @return 댓글 조회 성공 시 댓글 DTO 목록과 HTTP 상태 코드 200 (OK)을 반환합니다.
     *         만약 해당 문서가 없으면 예외가 발생합니다.
     */
    @Operation(summary = "댓글 조회", description = "특정 문서에 등록된 댓글 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "[{\"id\": 8, \"userId\": 2, \"comment\": \"허동운 빡빡이\", \"userName\": \"허동운\", \"userProfile\": \"https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F2Fblog.kakaocdn.net%2Fdn%2FcckdnY%2FbtqDogEdAS4%2F7kJZCk4ZhTYhNQMl6RkIU1%2Fimg.png\", \"likeCount\": 1, \"liked\": true}]"))),
            @ApiResponse(responseCode = "404", description = "글이 존재하지 않음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":404,\"message\":\"존재하지 않는 글입니다.\",\"errorCode\":\"Not Found\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/comments/{announcement-id}")
    public ResponseEntity<List<CommentDTO>> getComment(
            @Parameter(description = "공지 글 아이디", required = true)
            @PathVariable("announcement-id") Long announcementId) {

        List<CommentDTO> comments = announcementCommentServiceApplication.getComments(announcementId);  // 없으면 빈 리스트 반환
        return new ResponseEntity<>(comments, HttpStatus.OK);  // 댓글 리스트 반환
    }

    /**
     * 댓글 삭제 (DELETE)
     * 주어진 댓글(comment-id)을 삭제합니다.
     * 삭제는 댓글 작성자 또는 관리자 권한이 있는 경우에만 허용됩니다.
     *
     * @param commentId 삭제할 댓글의 고유 아이디.
     * @return 댓글 삭제 성공 시 HTTP 상태 코드 204 (No Content)을 반환합니다.
     * @throws PltecoException 댓글이 존재하지 않거나, 삭제 권한이 부족한 경우 예외가 발생합니다.
     */
    @Operation(summary = "댓글 삭제", description = "특정 댓글을 삭제합니다. (작성자 또는 관리자 권한 필요)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 부족",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":403,\"message\":\"삭제할 권한이 없습니다.\",\"errorCode\":\"FORBIDDEN\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
            @ApiResponse(responseCode = "404", description = "댓글이 존재하지 않음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":404,\"message\":\"존재하지 않는 댓글입니다.\",\"errorCode\":\"Not Found\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @DeleteMapping("/comments/{comment-id}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "댓글 아이디", required = true)
            @PathVariable("comment-id") Long commentId) {

        announcementCommentServiceApplication.deleteCommentByUser(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 삭제 후 204 반환
    }

    /**
     * 댓글 수정 (PATCH)
     * 주어진 댓글(comment-id)의 내용을 요청 본문에서 제공된 새로운 내용으로 수정합니다.
     * 수정은 댓글 작성자만 수행할 수 있습니다.
     *
     * @param commentId   수정할 댓글의 고유 아이디.
     * @param commentData 요청 본문으로 전달되는 수정할 댓글 DTO.
     * @return 댓글 수정 성공 시 HTTP 상태 코드 200 (OK)을 반환합니다.
     * @throws PltecoException 댓글이 존재하지 않거나, 수정 권한이 부족한 경우 예외가 발생합니다.
     */
    @Operation(summary = "댓글 수정", description = "특정 댓글의 내용을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 - 댓글 내용 누락 또는 500자 초과 등",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(value = "[{\"status\":400,\"message\":\"댓글 내용이 작성되지 않았습니다.\",\"errorCode\":\"Bad Request\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}," +
                                            "{\"status\":400,\"message\":\"댓글은 500자를 초과할 수 없습니다.\",\"errorCode\":\"Bad Request\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}]")})),
            @ApiResponse(responseCode = "403", description = "수정 권한 부족",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":403,\"message\":\"수정할 권한이 없습니다.\",\"errorCode\":\"FORBIDDEN\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
            @ApiResponse(responseCode = "404", description = "댓글이 존재하지 않음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":404,\"message\":\"댓글이 존재하지 않습니다.\",\"errorCode\":\"Not Found\",\"timestamp\":\"2025-02-04T02:30:22.220365\"}"))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PatchMapping("/comments/{comment-id}")
    public ResponseEntity<Void> updateComment(
            @Parameter(description = "댓글 아이디", required = true)
            @PathVariable("comment-id") Long commentId,
            @Valid @RequestBody RequestCommentDTO commentData) {

        String commentText = commentData.getCommentText();
        announcementCommentServiceApplication.updateComment(commentId, commentText);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 성공적으로 수정된 경우 204 반환
    }
}
