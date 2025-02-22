package org.plteco.ploytechcourse.application.announcement.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.plteco.ploytechcourse.application.comment.dto.CommentDTO;
import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.announcement.service.AnnouncementCommentServiceImpl;
import org.plteco.ploytechcourse.domain.announcement.service.AnnouncementService;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.like.commentlike.service.CommentLikeService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementCommentServiceApplication {

    private final AnnouncementCommentServiceImpl announcementCommentService;

    private final CommentLikeService commentLikeService;
    private final AnnouncementService announcementService;
    private final UserContextUtil userContextUtil;
    private final ModelMapper modelMapper;

    private User getCurrentUser() {
        return userContextUtil.getCurrentUser();
    }

    private Announcement getAnnouncement(long announcementId) {
        return announcementService.getAnnouncement(announcementId);
    }

    private Comment getComment(long commentId) {
        return announcementCommentService.getComment(commentId);
    }

    /** 댓글 생성 */
    public void createComment(long announcementId, String commentText) {
        User user = getCurrentUser();

        Announcement announcement = getAnnouncement(announcementId);

        announcementCommentService.createComment(user, announcement, commentText);
    }

    /** 댓글 수정 */
    public void updateComment(long commentId, String commentText) {
        User user = getCurrentUser();

        Comment comment = getComment(commentId);

        announcementCommentService.updateComment(user, comment, commentText);
    }

    /** 댓글 삭제 */
    public void deleteCommentByUser(long commentId) {
        User user = getCurrentUser();

        Comment comment = getComment(commentId);

        announcementCommentService.deleteCommentByUser(comment, user);
    }


    /** 댓글 조회 */
    public List<CommentDTO> getComments(long announcementId) {
        Announcement announcement = getAnnouncement(announcementId);

        List<Comment> comments = announcementCommentService.getComments(announcement);

        // Comment → CommentDTO 변환
        return comments.stream()
                .map(comment -> {
                    CommentDTO dto = modelMapper.map(comment, CommentDTO.class);
                    dto.setLiked(commentLikeService.isLiked(comment,getCurrentUser()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
