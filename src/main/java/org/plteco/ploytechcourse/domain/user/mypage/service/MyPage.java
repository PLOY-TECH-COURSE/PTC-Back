package org.plteco.ploytechcourse.domain.user.mypage.service;

import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;

public interface MyPage {
    String getName(Long id);
    String getBio(Long id);
    Long getNumberOfPosts(Long id);
    Long getNumberOfLove(Long id);
    String getProfile(Long id);
    Long getGeneration(Long id);
    String getUid(Long id);
    RoleEnum getRole(Long id);
}
