package org.plteco.ploytechcourse.application.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 테크코스 신청 정보를 조회할 때 반환되는 DTO 클래스입니다.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowApplicationDto {

    @Schema(description = "테크코스 신청 ID", example = "1")
    private Long id;

    @Schema(description = "신청한 사용자의 ID", example = "123")
    private Long user_id;

    @Schema(description = "신청한 사용자의 이름", example = "홍길동")
    private String name;

    @Schema(description = "신청한 사용자의 이메일", example = "hong@domain.com")
    private String email;

    @Schema(description = "신청한 사용자의 프로필 사진 URL", example = "http://example.com/profile.jpg")
    private String profile;

    @Schema(description = "신청자의 자기소개", example = "저는 개발에 관심이 많은 학생입니다.")
    private String introduction;

    @Schema(description = "신청자의 다짐", example = "저는 이 테크코스를 통해 많이 배우겠습니다.")
    private String resolution;
}
