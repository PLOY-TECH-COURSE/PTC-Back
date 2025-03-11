package org.plteco.ploytechcourse.domain.user.mypage.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.application.model.Student;
import org.plteco.ploytechcourse.domain.application.repository.StudentRepository;
import org.plteco.ploytechcourse.domain.document.repository.DocumentRepository;
import org.plteco.ploytechcourse.domain.like.commentlike.repository.CommentLikeRepository;
import org.plteco.ploytechcourse.domain.like.documentlike.repository.DocumentLikeRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
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
    private final DocumentLikeRepository documentLikeRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Override
    public String getName(Long id) {
        return userRepository.findById(id).get().getName();
    }

    @Override
    public String getBio(Long id) {
        return userRepository.findById(id).get().getBio();
    }

    @Override
    public Long getNumberOfPosts(Long id) {
        return Optional.ofNullable(documentRepository.countByUserId(id)).orElse(0L);
    }

    @Override
    public Long getNumberOfLove(Long id) {
        return Optional.ofNullable(documentLikeRepository.countTotalLikesByUserId(id)).orElse(0L);
    }

    @Override
    public String getProfile(Long id) {
        return userRepository.findById(id).get().getProfile();
    }

    @Override
    public Long getGeneration(Long id) {
        return studentRepository.findTechCourseIdByUserId(id).orElse(null);
    }

    @Override
    public String getUid(Long id) {
        return userRepository.findById(id).get().getUid();
    }

    @Override
    public RoleEnum getRole(Long id) {
        return userRepository.findById(id).get().getRole();
    }
}
