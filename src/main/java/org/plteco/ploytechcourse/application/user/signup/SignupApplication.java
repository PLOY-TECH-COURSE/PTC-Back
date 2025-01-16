package org.plteco.ploytechcourse.application.user.signup;

import org.plteco.ploytechcourse.application.user.signup.dto.SignupUserDto;

public interface SignupApplication {
    String signup(SignupUserDto signupUserDto);
}
