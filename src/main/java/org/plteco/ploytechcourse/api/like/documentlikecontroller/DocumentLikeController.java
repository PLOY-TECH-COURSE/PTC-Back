package org.plteco.ploytechcourse.api.like.documentlikecontroller;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.like.DocumentLikeServiceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/document-likes")
@RequiredArgsConstructor
public class DocumentLikeController {

    private final DocumentLikeServiceApplication documentLikeServiceApplication;

    @PostMapping("/{document-id}")
    public ResponseEntity<Void> like(@PathVariable("document-id") long documentId) {
        documentLikeServiceApplication.addLike(documentId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{document-id}")
    public ResponseEntity<Void> unlike(@PathVariable("document-id") long documentId) {
        documentLikeServiceApplication.removeLike(documentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
