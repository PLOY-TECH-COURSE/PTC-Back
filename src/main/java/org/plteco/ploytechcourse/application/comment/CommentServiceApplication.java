package org.plteco.ploytechcourse.application.comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
        if (user == null) {
            throw new PltecoException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 글입니다.", HttpStatus.NOT_FOUND));

        if (commentText == null || commentText.isBlank()) {
            throw new PltecoException("댓글 내용이 작성되지 않았습니다.", HttpStatus.BAD_REQUEST);
        }

        commentService.createComment(user, document, commentText);
    }

    /** 댓글 수정 */
    public void updateComment(long commentId, String commentText) {
        User user = userContextUtil.getCurrentUser();
        if (user == null) {
            throw new PltecoException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 댓글입니다.", HttpStatus.NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new PltecoException("해당 댓글을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        if (commentText == null || commentText.isBlank()) {
            throw new PltecoException("댓글 내용이 작성되지 않았습니다.", HttpStatus.BAD_REQUEST);
        }

        if(commentText.length() > 500){
            throw new PltecoException("댓글이 500자 이상입니다.",HttpStatus.BAD_REQUEST);
        }

        commentService.updateComment(user, commentId, commentText);
    }

    /** 댓글 삭제 */
    public void deleteCommentByUser(long commentId) {
        User user = userContextUtil.getCurrentUser();
        if (user == null) {
            throw new PltecoException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 댓글입니다.", HttpStatus.NOT_FOUND));

        /*
        * 댓글을 삭제하는 사람 권한이 ADMIN이거나
        * 댓글을 작성한 사람이 아닐 경우 오류 발생
         */
        if (!(user.getRole() == RoleEnum.ROLE_ADMIN) || !comment.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("해당 댓글을 삭제할 권한이 없습니다.");
        }

        commentService.deleteCommentByUser(commentId);
    }

    public List<Comment> getComments(long documentId) {
        if(!documentRepository.existsById(documentId)){
            throw new PltecoException("글이 존재하지 않습니다.",HttpStatus.NOT_FOUND);
        }
        return commentService.getComments(documentId);
    }
}
