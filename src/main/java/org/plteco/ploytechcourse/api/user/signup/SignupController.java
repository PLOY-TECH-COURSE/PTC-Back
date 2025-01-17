package org.plteco.ploytechcourse.api.user.signup;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.signup.SendEmailApplication;
import org.plteco.ploytechcourse.application.user.signup.SignupApplication;
import org.plteco.ploytechcourse.application.user.signup.dto.SignupUserDto;
import org.plteco.ploytechcourse.application.user.signup.dto.EmailDto;
import org.plteco.ploytechcourse.shared.jwt.service.UserContextUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {
    private final SignupApplication signupApplication;
    private final SendEmailApplication sendEmailApplication;
    private final UserContextUtil userContextUtil;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupUserDto signupUserDto) {
        return signupApplication.signup(signupUserDto);
    }

    @PostMapping("/email")
    public String mail(@RequestBody EmailDto emailDto) {
        return sendEmailApplication.sendEmail(emailDto);
    }
}

