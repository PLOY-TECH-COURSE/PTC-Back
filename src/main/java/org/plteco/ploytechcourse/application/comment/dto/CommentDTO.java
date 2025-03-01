package org.plteco.ploytechcourse.application.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDTO {
    private long id;
    private String comment;
    private String uid;
    private String userName;
    private String userProfile;
    private long likeCount;
    private boolean Liked;
}
