package org.plteco.ploytechcourse.application.comment;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.comment.service.CommentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceApplication {

    private final CommentService commentService;
    private final DocumentRepository documentRepository;
    public void createComment(long documentId,String commentText) {
        if (documentRepository.)
    }



}
