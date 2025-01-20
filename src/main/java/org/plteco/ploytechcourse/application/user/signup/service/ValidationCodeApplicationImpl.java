package org.plteco.ploytechcourse.application.user.signup.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.signup.dto.EmailAndCodeDto;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ValidationCodeApplicationImpl implements ValidationCodeApplication {

    private final ValidationService validationService;

    @Override
    public String isValid(EmailAndCodeDto emailAndCodeDto) {
        if (!validationService.verifyCode(emailAndCodeDto.getEmail(), emailAndCodeDto.getCode())) {
            return "코드가 이상합니다.";
        }
        return "코드가 맞습니다.";
    }
}
