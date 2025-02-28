package org.plteco.ploytechcourse.application.announcement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnnouncementUserInfoDTO {
    private String uid;
    private String name;
    private String profile;
}