package org.plteco.ploytechcourse.domain.announcement.service;

import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.announcement.model.entity.AnnouncementComment;
import org.plteco.ploytechcourse.domain.announcement.repository.AnnouncementCommentRepository;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.comment.repository.CommentRepository;
import org.plteco.ploytechcourse.domain.comment.service.CommentServiceImpl;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnnouncementCommentServiceImpl extends CommentServiceImpl implements AnnouncementCommentService {

    private final CommentRepository commentRepository;
    private final AnnouncementCommentRepository announcementCommentRepository;

    // 부모 클래스 생성자 호출
    public AnnouncementCommentServiceImpl(CommentRepository commentRepository, AnnouncementCommentRepository announcementCommentRepository) {
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.announcementCommentRepository = announcementCommentRepository;
    }

    @Override
    public List<AnnouncementComment> getComments(Announcement announcement) {
        return announcementCommentRepository.findCommentsByAnnouncement(announcement);
    }

    @Override
    public void createComment(User user, Announcement announcement, String commentText) {
        // 새로운 댓글 생성
        Comment comment = Comment.builder()
                .user(user)
                .comment(commentText)
                .build();

        AnnouncementComment announcementComment = AnnouncementComment.builder()
                .announcement(announcement)
                .comment(comment)
                .build();
        commentRepository.save(comment);
        announcementCommentRepository.save(announcementComment);
    }
}
