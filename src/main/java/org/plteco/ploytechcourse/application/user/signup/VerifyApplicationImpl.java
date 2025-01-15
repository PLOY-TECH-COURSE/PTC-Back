package org.plteco.ploytechcourse.application.user.signup;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.dto.emailAndCodeDto;
import org.plteco.ploytechcourse.domain.user.signup.service.SendEmailService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VerifyApplicationImpl implements VerifyApplication {

    private final SendEmailService sendEmailService;

    @Override
    public String verify(emailAndCodeDto emailAndCodeDto) {
        String email= emailAndCodeDto.getEmail();
        String code= emailAndCodeDto.getCode();
        boolean isValid = sendEmailService.verifyCode(email, code);
        if (isValid) {
            return "인증완료";
        } else {
            return "인증실패";
        }
    }
}
