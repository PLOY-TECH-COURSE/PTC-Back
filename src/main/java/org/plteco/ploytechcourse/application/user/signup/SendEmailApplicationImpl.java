package org.plteco.ploytechcourse.application.user.signup;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.dto.emailDto;
import org.plteco.ploytechcourse.domain.user.signup.service.SendEmailService;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SendEmailApplicationImpl implements SendEmailApplication {

    private final ValidationService validationService;
    private final SendEmailService sendEmailService;

    @Override
    public String sendEmail(emailDto emailDto) {
        if (!validationService.isValidEmail(emailDto.getEmail())) {
            return "이메일이 유효하지 않습니다.";
        }
        else{
            sendEmailService.sendCodeToEmail(emailDto.getEmail());
        }
        return "이메일로 인증번호를 발송했습니다.";
    }
}
