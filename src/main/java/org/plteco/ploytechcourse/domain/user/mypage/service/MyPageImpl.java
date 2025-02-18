package org.plteco.ploytechcourse.domain.user.mypage.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.application.model.Student;
import org.plteco.ploytechcourse.domain.application.repository.StudentRepository;
import org.plteco.ploytechcourse.domain.document.repository.DocumentRepository;
import org.plteco.ploytechcourse.domain.like.commentlike.repository.CommentLikeRepository;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageImpl implements MyPage {

    private final UserContextUtil userContextUtil;
    private final DocumentRepository documentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final StudentRepository studentRepository;

    @Override
    public String getName() {
        return userContextUtil.getCurrentUser().getName();
    }

    @Override
    public String getBio() {
        return userContextUtil.getCurrentUser().getBio();
    }

    @Override
    public Long getNumberOfPosts() {
        return Optional.ofNullable(documentRepository.countByUserId(userContextUtil.getId())).orElse(0L);
    }

    @Override
    public Long getNumberOfLove() {
        return Optional.ofNullable(commentLikeRepository.countTotalLikesByUserId(userContextUtil.getId())).orElse(0L);
    }

    @Override
    public String getProfile() {
        return userContextUtil.getCurrentUser().getProfile();
    }

    @Override
    public Long getGeneration() {
        return studentRepository.findTechCourseIdByUserId(userContextUtil.getId()).orElse(null);
    }

    @Override
    public String getUid() {
        return userContextUtil.getCurrentUser().getUid();
    }
}
