package org.plteco.ploytechcourse.application.user.signup;

import org.plteco.ploytechcourse.application.user.signup.dto.EmailDto;

public interface SendEmailApplication {
    String sendEmail(EmailDto emailDto);
}
