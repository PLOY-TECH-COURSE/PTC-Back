package org.plteco.ploytechcourse.api.comment;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.comment.service.CommentServiceImpl;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;
    private final UserContextUtil userContextUtil;

    // 댓글 등록 (POST)
    @PostMapping("/comments/{document-id}")
    public ResponseEntity<Void> registerComment(
            @PathVariable("document-id") Long documentId,
            @RequestBody Map<String, Object> commentData) {

        String commentText = (String) commentData.get("commentText");

        commentService.createComment(documentId, commentText);

        return new ResponseEntity<>(HttpStatus.CREATED);  // 성공적으로 생성된 경우 201 반환
    }

    // 댓글 조회 (GET)
    @GetMapping("/comments/{document-id}")
    public ResponseEntity<List<Comment>> getComment(@PathVariable("document-id") Long documentId) {

        List<Comment> comments = commentService.getComments(documentId);  // 없으면 빈 리스트 반환

        return new ResponseEntity<>(comments, HttpStatus.OK);  // 댓글 리스트 반환
    }

    // 댓글 삭제 (DELETE)
    @DeleteMapping("/comments/{comment-id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("comment-id") Long commentId) {
        Long userId = userContextUtil.getId();  // 로그인된 사용자 ID 가져오기

        // 댓글이 존재하는지 확인
        Comment comment = commentService.getCommentById(commentId);

        commentService.deleteComment(commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 삭제 후 204 반환
    }


    // 댓글 수정 (PATCH)
    @PatchMapping("/comments/{comment-id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable("comment-id") Long commentId,
            @RequestBody Map<String,Object> commentData) {

        String commentText = (String) commentData.get("commentText");

        commentService.updateComment(commentId, commentText);
        return new ResponseEntity<>(HttpStatus.OK);  // 수정 후 200 반환
    }
}
