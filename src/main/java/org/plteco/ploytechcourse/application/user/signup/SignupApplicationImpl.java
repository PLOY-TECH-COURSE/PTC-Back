package org.plteco.ploytechcourse.application.user.signup;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.signup.dto.SignupUserDto;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupApplicationImpl implements SignupApplication {

    private final ValidationService validationService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

        if(!validationService.verifyCode(signupUserDto.getEmail(), signupUserDto.getCode())) {
            return "코드가 이상합니다." ;
        }

        if(userRepository.existsByEmail(signupUserDto.getEmail())) {
            return "중복된 이름입니다.";
        }

        if(userRepository.existsByEmail(signupUserDto.getEmail())) {
            return "중복된 아이디입니다.";
        }
        userRepository.save(User.builder()
                        .uid(signupUserDto.getUid())
                        .email(signupUserDto.getEmail())
                        .name(signupUserDto.getName())
                        .bio(signupUserDto.getBio())
                        .grade(signupUserDto.getGrade())
                        .role(RoleEnum.ROLE_USER)
                        .classNumber(signupUserDto.getUserClass())
                        .profile("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcckdnY%2FbtqDogEdAS4%2F7kJZCk4ZhTYhNQMl6RkIU1%2Fimg.png")
                        .number(signupUserDto.getNumber())
                        .password(bCryptPasswordEncoder.encode(signupUserDto.getPassword()))
                        .build());
        return "유효성 검사 성공";
    }
}
