package org.plteco.ploytechcourse.application.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.comment.dto.CommentDTO;
import org.plteco.ploytechcourse.domain.comment.service.CommentServiceImpl;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.repository.DocumentRepository;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.comment.repository.CommentRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceApplication {

    private final CommentServiceImpl commentService;

    private final DocumentRepository documentRepository;
    private final CommentRepository commentRepository;
    private final UserContextUtil userContextUtil;

    /** 댓글 생성 */
    public void createComment(long documentId, String commentText) {
        User user = userContextUtil.getCurrentUser();

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 글입니다.", HttpStatus.NOT_FOUND));

        commentService.createComment(user, document, commentText);
    }

    /** 댓글 수정 */
    public void updateComment(long commentId, String commentText) {
        User user = userContextUtil.getCurrentUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 댓글입니다.", HttpStatus.NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new PltecoException("해당 댓글을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        commentService.updateComment(user, commentId, commentText);
    }

    /** 댓글 삭제 */
    public void deleteCommentByUser(long commentId) {
        User user = userContextUtil.getCurrentUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 댓글입니다.", HttpStatus.NOT_FOUND));

        /*
        * 댓글을 삭제하는 사람 권한이 ADMIN이거나
        * 댓글을 작성한 사람이 아닐 경우 오류 발생
         */
        if (!(user.getRole() == RoleEnum.ROLE_ADMIN || comment.getUser().getId().equals(user.getId()))) {
            throw new AccessDeniedException("해당 댓글을 삭제할 권한이 없습니다.");
        }

        commentService.deleteCommentByUser(commentId);
    }

    /** 댓글 조회 */
    public List<CommentDTO> getComments(long documentId) {
        if(!documentRepository.existsById(documentId)){
            throw new PltecoException("글이 존재하지 않습니다.",HttpStatus.NOT_FOUND);
        }

        List<Comment> comments = commentService.getComments(documentId);
        // Comment → CommentDTO 변환
        return comments.stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }
}
