package org.plteco.ploytechcourse.application.user.signup;

import org.plteco.ploytechcourse.domain.user.signup.model.dto.SignupUserDto;

public interface SignupApplication {
    String signup(SignupUserDto signupUserDto);
}
