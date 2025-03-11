package org.plteco.ploytechcourse.application.user.mypage.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.mypage.dto.ResponseMypage;
import org.plteco.ploytechcourse.domain.user.mypage.service.MyPage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageApplicationImpl implements MyPageApplication {

    private final MyPage myPage;

    @Override
    public ResponseMypage getMyPage(Long id) {
        return ResponseMypage.builder()
                .name(myPage.getName(id))
                .bio(myPage.getBio(id))
                .numberOfPosts(myPage.getNumberOfPosts(id))
                .numberOfLove(myPage.getNumberOfLove(id))
                .profile(myPage.getProfile(id))
                .generation(myPage.getGeneration(id))
                .uid(myPage.getUid(id))
                .role(myPage.getRole(id))
                .build();
    }
}
