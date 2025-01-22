package org.plteco.ploytechcourse.application.application.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowApplicationDto {

    private Long id;

    private Long user_id;

    private String name;

    private String email;

    private String profile;

    private String introduction;

    private String resolution;
}
