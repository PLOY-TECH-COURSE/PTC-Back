package org.plteco.ploytechcourse.domain.user.mypage.service;

public interface MyPage {
    String getName(Long id);
    String getBio(Long id);
    Long getNumberOfPosts(Long id);
    Long getNumberOfLove(Long id);
    String getProfile(Long id);
    Long getGeneration(Long id);
    String getUid(Long id);
}
