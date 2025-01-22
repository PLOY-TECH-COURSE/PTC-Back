package org.plteco.ploytechcourse.application.user.signup.service;

import org.plteco.ploytechcourse.application.user.signup.dto.EmailAndCodeDto;
import org.plteco.ploytechcourse.shared.exception.PltecoException;

public interface ValidationCodeApplication {
    void isValid(EmailAndCodeDto emailAndCodeDto);
}
