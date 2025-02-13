//package org.plteco.ploytechcourse.api.like.commentlikecontroller;
//
//import lombok.RequiredArgsConstructor;
//import org.plteco.ploytechcourse.domain.like.commentlike.service.CommentLikeService;
//import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/comment-likes")
//@RequiredArgsConstructor
//public class CommentLikeController {
//
//    private final CommentLikeService commentLikeService;
//
//    @DeleteMapping("/{comment-id}")
//    public ResponseEntity<String> unlike(@PathVariable("comment-id") long commentId) {
//        commentLikeService.removeLike(commentId);
//        return ResponseEntity.ok("좋아요가 삭제되었습니다.");
//    }
//
//    @PostMapping("/{comment-id}")
//    public ResponseEntity<String> like(@PathVariable("comment-id") long commentId) {
//
//        commentLikeService.addLike(commentId);
//        return ResponseEntity.ok("좋아요를 추가되었습니다.");
//    }
//
//    @GetMapping("{comment-id}")
//    public long getlikes(@PathVariable("comment-id") long commentId) {
//        return commentLikeService.getLikes(commentId);
//    }
//
//    @GetMapping("/{comment-id}/is-liked")
//    public boolean islike(@PathVariable("comment-id") long commentId) {
//        long userId = userContextUtil.getId();
//        return commentLikeService.isLiked(commentId, userId);
//    }
//}
