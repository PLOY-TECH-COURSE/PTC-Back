package org.plteco.ploytechcourse.domain.user.signup.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.repository.VerificationCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * 사용자 등록 관련 유효성 검사를 처리하는 서비스 클래스입니다.
 * 이메일, 비밀번호, 사용자 ID, 사용자 이름, 사용자 클래스, 학년, 번호에 대한 유효성 검사를 제공합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ValidationServiceImpl implements ValidationService {

    private final VerificationCodeRepository verificationCodeRepository;

    // 비밀번호 유효성 검사를 위한 패턴. 최소 1개의 알파벳, 특수문자, 숫자가 포함되어야 함.
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[~!@#$%^&*+=()_-])(?=.*[0-9]).+$");


    /**
     * 비밀번호와 확인용 비밀번호의 유효성을 검사합니다.
     * 비밀번호는 최소 길이, 최대 길이, 특수 문자, 숫자 및 알파벳 포함 조건을 충족해야 하며, 비밀번호와 ID가 일치하지 않아야 합니다.
     *
     * @param password 비밀번호
     * @param rePassword 확인용 비밀번호
     * @param UID 사용자 ID
     * @return 비밀번호가 유효하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean isValidPassword(String password, String rePassword, String UID) {
        if (!password.equals(rePassword)) {
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return false;
        }
        else return !password.contains(UID);
    }





    /**
     * 이메일과 인증 코드가 일치하는지 확인하고, 코드가 만료되지 않았는지 검사합니다.
     *
     * @param email 인증을 확인할 이메일 주소
     * @param code 인증 코드
     * @return 코드가 유효하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean verifyCode(String email, String code) {
        return verificationCodeRepository.findByEmailAndCode(email, code)
                .map(vc -> vc.getExpiresTime().isAfter(LocalDateTime.now()))
                .orElse(false);
    }
}
