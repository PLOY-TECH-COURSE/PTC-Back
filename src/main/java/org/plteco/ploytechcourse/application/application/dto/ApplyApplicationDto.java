package org.plteco.ploytechcourse.application.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 테크코스 신청 시 사용자가 제출하는 자기소개 및 다짐을 담고 있는 DTO 클래스입니다.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplyApplicationDto {

    @Schema(description = "테크코스 신청자의 자기소개", example = "안녕하세요 저는 선배님의 개가 되기위해 개 대학을 졸업했습니다.")
    @NotNull
    @Size(min = 1, max = 500)
    private String introduction;

    @Schema(description = "기술경험", example = "스프링 써봄ㅋ")
    @NotNull
    @Size(min = 1, max = 500)
    private String skill;

    @Schema(description = "배우고싶은것", example = "코루틴 써보고 싶음")
    @NotNull
    @Size(min = 1, max = 500)
    private String study;

    @Schema(description = "기대하는점", example = "세계 정복을 꿈꾸고 있습니다.")
    @NotNull
    @Size(min = 1, max = 500)
    private String expectation;
}
