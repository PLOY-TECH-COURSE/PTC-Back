package org.plteco.ploytechcourse.application.user.repassword.service;

import org.plteco.ploytechcourse.application.user.repassword.dto.PasswordRequestDTO;
import org.plteco.ploytechcourse.application.user.signup.dto.EmailAndCodeDto;
import org.plteco.ploytechcourse.application.user.signup.dto.EmailDto;

public interface EmailValidation {
    void validate(EmailAndCodeDto emailAndCodeDto);
    void sendEmail(EmailDto emailDto);
    void change(PasswordRequestDTO requestDTO);
}
