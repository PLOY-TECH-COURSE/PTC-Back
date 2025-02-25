package org.plteco.ploytechcourse.comment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.plteco.ploytechcourse.application.document.service.DocumentCommentServiceApplication;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.comment.repository.CommentRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private DocumentCommentServiceApplication commentService;

    @Test
    public void testCreateComment() {
        // given
        User user = Mockito.mock(User.class);
        Long documentId = 1L;
        String commentText = "comment";

        // when
        commentService.createComment(documentId, commentText);

        // then
        // Verify that save() is called once
        verify(commentRepository, times(1)).save(Mockito.any(Comment.class));
    }



    @Test
    public void testDeleteComment() {
        // given
        Long commentId = 1L;

        // when
        commentService.deleteCommentByUser(commentId);

        // then
        // Verify that deleteById() is called once
        verify(commentRepository, times(1)).deleteById(commentId);
    }



}
