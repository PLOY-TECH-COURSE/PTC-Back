package org.plteco.ploytechcourse.application.comment.dto;

import lombok.Getter;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

@Getter
public class CommentDTO {
    private long id;
    private long userId;
    private String comment;
    private String userName;
    private String userProfile;
    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.comment = comment.getComment();
        this.userName = comment.getUser().getName();
        this.userProfile = comment.getUser().getProfile();
    }
}
