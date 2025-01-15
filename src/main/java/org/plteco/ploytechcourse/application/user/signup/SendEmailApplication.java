package org.plteco.ploytechcourse.application.user.signup;

import org.plteco.ploytechcourse.domain.user.signup.model.dto.emailDto;

public interface SendEmailApplication {
    String sendEmail(emailDto emailDto);
}
