package org.plteco.ploytechcourse.api.user.signup;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.plteco.ploytechcourse.application.user.signup.Signup;
import org.plteco.ploytechcourse.domain.user.signup.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {
    private final Signup signup;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
       signup.signup(user);
       return "success";
    }
}
