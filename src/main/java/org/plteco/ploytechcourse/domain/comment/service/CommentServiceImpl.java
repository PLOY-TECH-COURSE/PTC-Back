package org.plteco.ploytechcourse.domain.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.comment.dto.CommentDTO;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.comment.repository.CommentRepository;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
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

        List<Comment> comments = commentRepository.findByDocumentId(documentId);

        return comments;
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
    public void deleteCommentByUser(long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void updateComment(User user, long commentId, String commentText) {

        Optional<Comment> oldComment = commentRepository.findById(commentId);

        if (oldComment.isPresent()) {
            Comment newComment = Comment.builder()
                    .id(oldComment.get().getId())
                    .user(user)
                    .document(oldComment.get().getDocument())
                    .comment(commentText)
                    .build();

            commentRepository.save(newComment); // JPA의 save메서드는 update 기능까지 지원
        }
    }
}
