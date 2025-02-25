package org.plteco.ploytechcourse.domain.announcement.service;

import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.announcement.model.entity.AnnouncementComment;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.util.List;

public interface AnnouncementCommentService {
    List<AnnouncementComment> getComments(Announcement announcement);
    void createComment(User user, Announcement announcement, String commentText);
}
