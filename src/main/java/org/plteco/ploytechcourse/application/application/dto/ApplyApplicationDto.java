package org.plteco.ploytechcourse.application.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplyApplicationDto {
    @NotNull
    @Size(min = 1, max = 500)
    private String introduction;

    @NotNull
    @Size(min = 1, max = 500)
    private String resolution;
}
