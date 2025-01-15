package org.plteco.ploytechcourse.application.user.signup;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.dto.SignupUserDto;
import org.plteco.ploytechcourse.domain.user.signup.service.SendEmailServiceImpl;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupApplicationImpl implements SignupApplication {

    private final ValidationService validationService;
    private final SendEmailServiceImpl sendMailImpl;

    @Override
    public String signup(SignupUserDto signupUserDto) {
        // 이메일 유효성 검사
        if (!validationService.isValidEmail(signupUserDto.getEmail())) {
            return "이메일이 유효하지 않습니다.";
        }

        // 비밀번호 유효성 검사
        if (!validationService.isValidPassword(signupUserDto.getPassword(), signupUserDto.getRePassword(), signupUserDto.getUid())) {
            return "비밀번호가 유효하지 않습니다.";
        }

        // ID 유효성 검사
        if (!validationService.isValidID(signupUserDto.getUid())) {
            return "ID가 유효하지 않습니다.";
        }

        // 사용자 이름 유효성 검사
        if (!validationService.isValidUsername(signupUserDto.getName())) {
            return "사용자 이름이 유효하지 않습니다.";
        }

        // 사용자 학년 유효성 검사
        if (!validationService.isValidGrade(signupUserDto.getGrade())) {
            return "학년이 유효하지 않습니다.";
        }

        // 사용자 학급 유효성 검사
        if (!validationService.isValidUserClass(signupUserDto.getUserClass())) {
            return "학급이 유효하지 않습니다.";
        }

        // 사용자 번호 유효성 검사
        if (!validationService.isValidNumber(signupUserDto.getNumber())) {
            return "번호가 유효하지 않습니다.";
        }

        return "유효성 검사 성공";
    }
}
