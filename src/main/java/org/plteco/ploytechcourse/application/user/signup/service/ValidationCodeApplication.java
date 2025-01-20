package org.plteco.ploytechcourse.application.user.signup.service;

import org.plteco.ploytechcourse.application.user.signup.dto.EmailAndCodeDto;

public interface ValidationCodeApplication {
    String isValid(EmailAndCodeDto emailAndCodeDto);
}
