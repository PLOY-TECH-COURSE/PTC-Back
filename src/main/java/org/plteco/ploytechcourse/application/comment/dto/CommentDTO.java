package org.plteco.ploytechcourse.application.comment.dto;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
@Setter
@Getter
public class CommentDTO {
    private long id;
    private String comment;
    private long userId;
    private String userName;
    private String userProfile;
    private long likeCount;
    private boolean Liked;
}
