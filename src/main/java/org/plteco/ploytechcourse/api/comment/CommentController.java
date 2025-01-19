package org.plteco.ploytechcourse.api.comment;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final UserContextUtil userContextUtil;

    @PostMapping("/comments/{document-id}")
    public void registerComment(@PathVariable("document-id") long documentId){

    }

    @GetMapping("/comments/{document-id}")
    public List<Comment> getComment(@PathVariable("document-id") long documentId){

    }

    @PatchMapping("/comments/{document-id}/{comment-id}")
    public void updateComment(@PathVariable("document-id") long documentId, @PathVariable("comment-id") long commentId, @RequestBody String commentText){

    }

    @DeleteMapping("/comments/{document-id}/{comment-id}")
    public void deleteComment(@PathVariable("document-id") long documentId, @PathVariable("comment-id") long commentId){

    }
}
