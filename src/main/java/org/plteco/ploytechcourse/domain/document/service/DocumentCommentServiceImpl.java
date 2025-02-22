package org.plteco.ploytechcourse.domain.document.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.announcement.repository.AnnouncementCommentRepository;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.comment.repository.CommentRepository;
import org.plteco.ploytechcourse.domain.comment.service.CommentService;
import org.plteco.ploytechcourse.domain.comment.service.CommentServiceImpl;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.DocumentComment;
import org.plteco.ploytechcourse.domain.document.repository.DocumentCommentRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DocumentCommentServiceImpl extends CommentServiceImpl implements DocumentCommentService {

    private final CommentRepository commentRepository;
    private final DocumentCommentRepository documentCommentRepository;

    public DocumentCommentServiceImpl(CommentRepository commentRepository, DocumentCommentRepository documentCommentRepository) {
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.documentCommentRepository = documentCommentRepository;
    }

    public List<Comment> getComments(Document document) {
        return commentRepository.findByDocumentId(document.getId());
    }

    public void createComment(User user, Document document, String commentText) {

        Comment comment = Comment.builder()
                .user(user)
                .comment(commentText)
                .build();

        DocumentComment documentComment = DocumentComment.builder()
                .document(document)
                .comment(comment)
                .build();

        commentRepository.save(comment);
        documentCommentRepository.save(documentComment);
    }

}
