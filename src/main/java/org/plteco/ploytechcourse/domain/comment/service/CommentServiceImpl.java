package org.plteco.ploytechcourse.domain.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.comment.dto.CommentDTO;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.comment.repository.CommentRepository;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getComments(long documentId) {

        return commentRepository.findByDocumentId(documentId);
    }

    @Override
    public void createComment(User user, Document document, String commentText) {

        Comment comment = Comment.builder()
                .user(user)
                .document(document)
                .comment(commentText)
                .build();

        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentByUser(long commentId,User user) {
        Comment comment = getComment(commentId);

        if (!(user.getRole() == RoleEnum.ROLE_ADMIN || comment.getUser().getId().equals(user.getId()))) {
            throw new AccessDeniedException("해당 댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public void updateComment(User user, long commentId, String commentText) {

        Comment oldComment = getComment(commentId);

        if (!oldComment.getUser().getId().equals(user.getId())) {
            throw new PltecoException("해당 댓글을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        Comment newComment = Comment.builder()
                .id(oldComment.getId())
                .user(user)
                .document(oldComment.getDocument())
                .comment(commentText)
                .build();

        commentRepository.save(newComment); // JPA의 save메서드는 update 기능까지 지원
    }

    @Override
    public Comment getComment(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 댓글입니다", HttpStatus.NOT_FOUND));
    }
}
