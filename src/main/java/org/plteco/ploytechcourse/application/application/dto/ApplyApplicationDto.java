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

    @Schema(description = "테크코스 신청자의 다짐", example = "제가 뽑힌다면 선배님을 위해 가방을 매일매일 들고다니겠습니다.")
    @NotNull
    @Size(min = 1, max = 500)
    private String resolution;
}
