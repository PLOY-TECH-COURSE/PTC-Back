package org.plteco.ploytechcourse.application.comment.dto;

import lombok.Getter;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;

@Getter
public class CommentDTO {
    private long id;
    private long userId;
    private String comment;
    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.comment = comment.getComment();
    }
}
