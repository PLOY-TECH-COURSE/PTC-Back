package org.plteco.ploytechcourse.application.user.realMyPage.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.mypage.dto.ResponseMypage;
import org.plteco.ploytechcourse.domain.user.mypage.service.MyPage;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RealMyPageServiceApplicationImpl implements RealMyPageServiceApplication {
    private final UserContextUtil userContextUtil;
    private final MyPage myPage;

    @Override
    public ResponseMypage getMyPage() {
        Long id=userContextUtil.getCurrentUser().getId();
        return ResponseMypage.builder()
                .name(myPage.getName(id))
                .bio(myPage.getBio(id))
                .numberOfPosts(myPage.getNumberOfPosts(id))
                .numberOfLove(myPage.getNumberOfLove(id))
                .profile(myPage.getProfile(id))
                .generation(myPage.getGeneration(id))
                .uid(myPage.getUid(id))
                .build();
    }
}
