package org.plteco.ploytechcourse.domain.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.comment.repository.CommentRepository;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserContextUtil userContextUtil;

    @Override
    public List<Comment> getComments(Long document_id) {

        List<Comment> comments = commentRepository.findCommentByDocumentId(document_id);

        return comments.isEmpty() ? null : comments;
    }

    @Override
    public void createComment(Long document_id, String commentText) {

        Comment comment = Comment.builder()
                .user(userContextUtil.getCurrentUser())
                .documentId(document_id)
                .comment(commentText)
                .build();

        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void updateComment(Long commentId, String commentText) {

        Optional<Comment> oldComment = commentRepository.findById(commentId);

        if (oldComment.isPresent()) {
            Comment newComment = Comment.builder()
                    .id(oldComment.get().getId())
                    .user(userContextUtil.getCurrentUser())
                    .documentId(oldComment.get().getDocumentId())
                    .comment(commentText)
                    .build();

            commentRepository.save(newComment); // JPA의 save메서드는 update 기능까지 지원
        }
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }
}
