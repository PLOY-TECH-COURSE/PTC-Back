package org.plteco.ploytechcourse.application.user.signup.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.signup.dto.SignupUserDto;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
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
     */
    @Override
    public void signup(SignupUserDto signupUserDto) {


        // 비밀번호 유효성 검사
        if (!validationService.isValidPassword(signupUserDto.getPassword(), signupUserDto.getRePassword(), signupUserDto.getUid())) {
            throw new PltecoException("비밀번호가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
        }

        // 이메일 인증 코드 유효성 검사
        if (!validationService.verifyCode(signupUserDto.getEmail(), signupUserDto.getCode())) {
            throw new PltecoException("코드가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
        }

        // 이메일 중복 검사
        if (userRepository.existsByEmail(signupUserDto.getEmail())) {
            throw new PltecoException("이메일이 중복되었습니다.", HttpStatus.BAD_REQUEST);
        }

        // 아이디 중복 검사
        if (userRepository.existsByUid(signupUserDto.getUid())) {
            throw new PltecoException("아이디가 중복되었습니다.", HttpStatus.BAD_REQUEST);
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
        throw new PltecoException("회원가입 성공", HttpStatus.OK);
    }
}
