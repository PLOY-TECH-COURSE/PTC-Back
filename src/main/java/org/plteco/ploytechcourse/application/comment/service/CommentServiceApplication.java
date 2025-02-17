package org.plteco.ploytechcourse.application.comment.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.plteco.ploytechcourse.application.comment.dto.CommentDTO;
import org.plteco.ploytechcourse.domain.comment.service.CommentServiceImpl;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.repository.DocumentRepository;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.comment.repository.CommentRepository;
import org.plteco.ploytechcourse.domain.like.commentlike.service.CommentLikeService;
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

    private final CommentLikeService commentLikeService;
    private final DocumentRepository documentRepository;
    private final UserContextUtil userContextUtil;
    private final ModelMapper modelMapper;

    private User getCurrentUser() {
        return userContextUtil.getCurrentUser();
    }

    private Document getDocument(long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 글입니다.", HttpStatus.NOT_FOUND));
    }

    /** 댓글 생성 */
    public void createComment(long documentId, String commentText) {
        User user = getCurrentUser();

        Document document = getDocument(documentId);

        commentService.createComment(user, document, commentText);
    }

    /** 댓글 수정 */
    public void updateComment(long commentId, String commentText) {
        User user = getCurrentUser();

        Comment comment = commentService.getComment(commentId);

        commentService.updateComment(user, commentId, commentText);
    }

    /** 댓글 삭제 */
    public void deleteCommentByUser(long commentId) {
        User user = getCurrentUser();

        commentService.deleteCommentByUser(commentId, user);
    }


    /** 댓글 조회 */
    public List<CommentDTO> getComments(long documentId) {
        if(!documentRepository.existsById(documentId)){
            throw new PltecoException("글이 존재하지 않습니다.",HttpStatus.NOT_FOUND);
        }

        List<Comment> comments = commentService.getComments(documentId);

        // Comment → CommentDTO 변환
        return comments.stream()
                .map(comment -> {
                    CommentDTO dto = modelMapper.map(comment, CommentDTO.class);
                    dto.setLikeCount(commentLikeService.getLikes(comment));
                    dto.setLiked(commentLikeService.isLiked(comment,getCurrentUser()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
