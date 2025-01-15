package org.plteco.ploytechcourse.application.user.signup;

import org.plteco.ploytechcourse.domain.user.signup.model.dto.emailAndCodeDto;

public interface VerifyApplication {
    String verify(emailAndCodeDto emailAndCodeDto);
}
