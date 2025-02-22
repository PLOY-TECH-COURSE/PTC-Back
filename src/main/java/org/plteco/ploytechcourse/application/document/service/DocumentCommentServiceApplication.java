package org.plteco.ploytechcourse.application.document.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.plteco.ploytechcourse.application.comment.dto.CommentDTO;
import org.plteco.ploytechcourse.domain.document.service.DocumentCommentServiceImpl;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.document.service.DocumentService;
import org.plteco.ploytechcourse.domain.like.commentlike.service.CommentLikeService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentCommentServiceApplication {

    private final DocumentCommentServiceImpl documentCommentService;

    private final CommentLikeService commentLikeService;
    private final DocumentService documentService;
    private final UserContextUtil userContextUtil;
    private final ModelMapper modelMapper;

    private User getCurrentUser() {
        return userContextUtil.getCurrentUser();
    }

    private Document getDocument(long documentId) {
        return documentService.getDocument(documentId);
    }

    private Comment getComment(long commentId) {
        return documentCommentService.getComment(commentId);
    }

    /** 댓글 생성 */
    public void createComment(long documentId, String commentText) {
        User user = getCurrentUser();

        Document document = getDocument(documentId);

        documentCommentService.createComment(user, document, commentText);
    }

    /** 댓글 수정 */
    public void updateComment(long commentId, String commentText) {
        User user = getCurrentUser();

        Comment comment = getComment(commentId);

        documentCommentService.updateComment(user, comment, commentText);
    }

    /** 댓글 삭제 */
    public void deleteCommentByUser(long commentId) {
        User user = getCurrentUser();

        Comment comment = getComment(commentId);

        documentCommentService.deleteCommentByUser(comment, user);
    }


    /** 댓글 조회 */
    public List<CommentDTO> getComments(long documentId) {
        Document document = getDocument(documentId);

        List<Comment> comments = documentCommentService.getComments(document);

        // Comment → CommentDTO 변환
        return comments.stream()
                .map(comment -> {
                    CommentDTO dto = modelMapper.map(comment, CommentDTO.class);
                    dto.setLiked(commentLikeService.isLiked(comment,getCurrentUser()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
