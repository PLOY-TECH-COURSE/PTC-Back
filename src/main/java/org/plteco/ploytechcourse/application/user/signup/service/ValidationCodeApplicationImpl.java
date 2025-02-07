package org.plteco.ploytechcourse.application.user.signup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.plteco.ploytechcourse.application.user.signup.dto.EmailAndCodeDto;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ValidationCodeApplicationImpl implements ValidationCodeApplication {

    private final ValidationService validationService;

    @Override
    public void isValid(EmailAndCodeDto emailAndCodeDto) {
        log.info("이메일과 코드 검증 시작: 이메일={}, 코드={}", emailAndCodeDto.getEmail(), emailAndCodeDto.getCode());

        boolean isValid = validationService.verifyCode(emailAndCodeDto.getEmail(), emailAndCodeDto.getCode());
        if (!isValid) {
            log.error("잘못된 코드: 이메일={}, 코드={}", emailAndCodeDto.getEmail(), emailAndCodeDto.getCode());
            throw new PltecoException("잘못된 인증 코드입니다.", HttpStatus.BAD_REQUEST);
        }

        log.info("올바른 코드입니다: 이메일={}, 코드={}", emailAndCodeDto.getEmail(), emailAndCodeDto.getCode());
        throw new PltecoException("올바른 코드입니다.", HttpStatus.OK);
    }
}
