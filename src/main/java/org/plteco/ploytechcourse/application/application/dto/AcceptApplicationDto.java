package org.plteco.ploytechcourse.application.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcceptApplicationDto {
    @NotNull
    private Long userId;

    @NotNull
    private Long generation;

    @NotNull
    private Long trackId;
}
