package org.plteco.ploytechcourse.domain.announcement.service;

import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.util.List;

public interface AnnouncommentService {
    List<Comment> getComments(Announcement announcement);
    void createComment(User user, Announcement announcement, String commentText);
}
