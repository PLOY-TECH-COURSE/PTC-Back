package org.plteco.ploytechcourse.application.user.signup.service;

import org.plteco.ploytechcourse.application.user.signup.dto.SignupUserDto;
import org.plteco.ploytechcourse.shared.exception.PltecoException;

/**
 * 사용자 회원가입을 처리하는 애플리케이션 서비스 인터페이스입니다.
 * <p>
 * 이 인터페이스는 회원가입 관련된 비즈니스 로직을 정의하고,
 * 실제 회원가입 처리를 구현하는 클래스에서 이를 구현합니다.
 * </p>
 */
public interface SignupApplication {

    /**
     * 주어진 사용자 정보를 기반으로 회원가입을 처리합니다.
     * <p>
     * 회원가입 처리 시 사용자 정보를 검증하고, 유효하다면 회원가입을 완료합니다.
     * </p>
     *
     * @param signupUserDto 회원가입에 필요한 사용자 정보가 담긴 DTO 객체
     * @return 회원가입 처리 결과 메시지
     */
    void signup(SignupUserDto signupUserDto);
}
