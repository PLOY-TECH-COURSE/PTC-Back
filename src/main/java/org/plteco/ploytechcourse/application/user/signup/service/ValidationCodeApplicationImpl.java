package org.plteco.ploytechcourse.application.user.signup.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.signup.dto.EmailAndCodeDto;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ValidationCodeApplicationImpl implements ValidationCodeApplication {

    private final ValidationService validationService;

    @Override
    public void isValid(EmailAndCodeDto emailAndCodeDto) {
        if (!validationService.verifyCode(emailAndCodeDto.getEmail(), emailAndCodeDto.getCode())) {
           throw new PltecoException(HttpStatus.BAD_REQUEST);
        }
        throw new PltecoException("올바른 코드입니다.", HttpStatus.OK);
    }
}
