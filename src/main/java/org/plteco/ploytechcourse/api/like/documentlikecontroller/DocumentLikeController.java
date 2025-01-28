package org.plteco.ploytechcourse.api.like.documentlikecontroller;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.like.documentlike.service.DocumentLikeService;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/document-likes")
@RequiredArgsConstructor
public class DocumentLikeController {

    private final DocumentLikeService documentLikeService;
    private final UserContextUtil userContextUtil;

    @PostMapping("/{document-id}")
    public void like(@PathVariable("document-id") long documentId) {
        long userId = userContextUtil.getId();
        documentLikeService.addLike(documentId, userId);
    }

    @DeleteMapping("/{document-id}")
    public void unlike(@PathVariable("document-id") long documentId) {
        long userId = userContextUtil.getId();
        documentLikeService.removeLike(documentId, userId);
    }

    @GetMapping("/{document-id}")
    public long getlikes(@PathVariable("document-id") long documentId) {
        return documentLikeService.getLikes(documentId);
    }

    @GetMapping("/like/{document-id}")
    public boolean isLiked(@PathVariable("document-id") long documentId) {
        long userId = userContextUtil.getId();
        return documentLikeService.isLiked(documentId,userId);
    }
}
