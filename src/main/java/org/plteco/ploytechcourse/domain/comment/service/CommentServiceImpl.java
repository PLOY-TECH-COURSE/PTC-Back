package org.plteco.ploytechcourse.domain.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public void deleteCommentByUser(Comment comment,User user) {
        if (!(user.getRole() == RoleEnum.ROLE_ADMIN || comment.getUser().getId().equals(user.getId()))) {
            throw new AccessDeniedException("해당 댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    @Override
    public void updateComment(User user, Comment oldComment, String commentText) {
        boolean isOwner = oldComment.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == RoleEnum.ROLE_SUPERADMIN || user.getRole() == RoleEnum.ROLE_ADMIN;
        if (!isOwner && !isAdmin) { // 🚨 작성자가 아니고, 관리자도 아니라면 수정 권한 없음
            throw new PltecoException("해당 댓글을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        oldComment.setComment(commentText);
    }

    @Override
    public Comment getComment(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new PltecoException("존재하지 않는 댓글입니다", HttpStatus.NOT_FOUND));
    }
}
