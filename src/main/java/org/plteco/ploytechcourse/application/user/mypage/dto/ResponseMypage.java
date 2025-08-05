package org.plteco.ploytechcourse.application.user.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMypage {
    private String name;
    private String bio;
    private Long numberOfPosts;
    private Long numberOfLove;
    private String profile;
    private Integer generation;
    private String uid;
    private RoleEnum role;
}
