package org.plteco.ploytechcourse.application.user.signup;

import org.plteco.ploytechcourse.application.user.signup.dto.emailDto;

public interface SendEmailApplication {
    String sendEmail(emailDto emailDto);
}
