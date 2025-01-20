package org.plteco.ploytechcourse.domain.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getComments(long document_id) {

        List<Comment> comments = commentRepository.findCommentByDocumentId(document_id);

        return comments.isEmpty() ? null : comments;
    }

    @Override
    public void createComment(long user_id, long document_id, String commentText) {

        Comment comment = Comment.builder()
                .user_id(user_id)
                .documentId(document_id)
                .comment(commentText)
                .build();

        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void updateComment(long commentId, String commentText) {

        Optional<Comment> oldComment = commentRepository.findById(commentId);

        if (oldComment.isPresent()) {
            Comment newComment = oldComment.get();
            newComment.setComment(commentText);

            commentRepository.save(newComment);
        }
    }
}
