package org.plteco.ploytechcourse.application.user.signup.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.signup.dto.SignupUserDto;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 회원가입을 처리하는 애플리케이션 서비스 구현 클래스입니다.
 * <p>
 * 이 클래스는 사용자가 제공한 회원가입 정보를 유효성 검사 후,
 * 유효한 경우 해당 정보를 저장하고 사용자 계정을 생성하는 역할을 수행합니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SignupApplicationImpl implements SignupApplication {

    private final ValidationService validationService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 주어진 사용자 정보를 기반으로 회원가입을 처리합니다.
     * <p>
     * 이메일, 비밀번호, ID, 사용자 이름, 학년 등 각 항목에 대해 유효성 검사를 진행하며,
     * 모든 검사를 통과하면 사용자를 저장합니다.
     * </p>
     *
     * @param signupUserDto 회원가입에 필요한 사용자 정보가 담긴 DTO 객체
     * @return 회원가입 처리 결과 메시지
     */
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

        // 이메일 인증 코드 유효성 검사
        if (!validationService.verifyCode(signupUserDto.getEmail(), signupUserDto.getCode())) {
            return "코드가 이상합니다.";
        }

        // 이메일 중복 검사
        if (userRepository.existsByEmail(signupUserDto.getEmail())) {
            return "중복된 이름입니다.";
        }

        // 아이디 중복 검사
        if (userRepository.existsByUid(signupUserDto.getUid())) {
            return "중복된 아이디입니다.";
        }

        // 사용자 정보 저장
        userRepository.save(User.builder()
                .uid(signupUserDto.getUid())
                .email(signupUserDto.getEmail())
                .name(signupUserDto.getName())
                .bio(signupUserDto.getBio())
                .grade(signupUserDto.getGrade())
                .role(RoleEnum.ROLE_USER) // 기본 역할은 사용자
                .classNumber(signupUserDto.getUserClass())
                .profile("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcckdnY%2FbtqDogEdAS4%2F7kJZCk4ZhTYhNQMl6RkIU1%2Fimg.png") // 기본 프로필 이미지 URL
                .number(signupUserDto.getNumber())
                .password(bCryptPasswordEncoder.encode(signupUserDto.getPassword())) // 비밀번호 암호화
                .build());

        // 회원가입 성공 메시지 반환
        return "유효성 검사 성공";
    }
}
