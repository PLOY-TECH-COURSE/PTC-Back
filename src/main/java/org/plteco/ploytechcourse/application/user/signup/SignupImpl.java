package org.plteco.ploytechcourse.application.user.signup;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.dto.User;
import org.plteco.ploytechcourse.domain.user.signup.service.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupImpl implements Signup {

    private final Validation validation;

    @Override
    public String signup(User user) {
        // 유효성 검사
        if (!validation.isValidEmail(user.getEmail())) {
            return "이메일이 유효하지 않습니다.";
        }
        if (!validation.isValidPassword(user.getPassword(), user.getRePassword(), user.getUID())) {
            return "비밀번호가 유효하지 않습니다.";
        }
        if (!validation.isValidID(user.getUID())) {
            return "ID가 유효하지 않습니다.";
        }
        if (!validation.isValidUsername(user.getName())) {
            return "사용자 이름이 유효하지 않습니다.";
        }
        return "유효성 검사 성공";
    }
}

