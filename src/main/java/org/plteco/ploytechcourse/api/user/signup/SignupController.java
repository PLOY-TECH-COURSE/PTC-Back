package org.plteco.ploytechcourse.api.user.signup;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.signup.SendEmailApplication;
import org.plteco.ploytechcourse.application.user.signup.SignupApplication;
import org.plteco.ploytechcourse.application.user.signup.VerifyApplication;
import org.plteco.ploytechcourse.domain.user.signup.model.dto.SignupUserDto;
import org.plteco.ploytechcourse.domain.user.signup.model.dto.emailDto;
import org.plteco.ploytechcourse.domain.user.signup.model.dto.emailAndCodeDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {
    private final SignupApplication signupApplication;
    private final VerifyApplication verifyApplication;
    private final SendEmailApplication sendEmailApplication;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupUserDto signupUserDto) {
        return signupApplication.signup(signupUserDto);
    }

    @PostMapping("/mail")
    public String mail(@RequestBody emailDto emailDto) {
        return sendEmailApplication.sendEmail(emailDto);
    }

    @PostMapping("/verify")
    public String verify(@RequestBody emailAndCodeDto emailAndCodeDto) {
        return verifyApplication.verify(emailAndCodeDto);
    }
}

