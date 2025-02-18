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
    public ResponseMypage getMyPage() {
        return ResponseMypage.builder()
                .name(myPage.getName())
                .bio(myPage.getBio())
                .numberOfPosts(myPage.getNumberOfPosts())
                .numberOfLove(myPage.getNumberOfLove())
                .profile(myPage.getProfile())
                .generation(myPage.getGeneration())
                .uid(myPage.getUid())
                .build();
    }
}
