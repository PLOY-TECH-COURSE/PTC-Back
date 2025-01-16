package org.plteco.ploytechcourse.domain.user.signup.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.repository.VerificationCodeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * 사용자 등록 관련 유효성 검사를 처리하는 서비스 클래스입니다.
 * 이메일, 비밀번호, 사용자 ID, 사용자 이름, 사용자 클래스, 학년, 번호에 대한 유효성 검사를 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final int PASSWORD_MAX = 30;
    private final int PASSWORD_MIN = 10;
    private final int NAME_MAX = 30;
    private final int NAME_MIN = 2;
    private final int UID_MAX = 15;
    private final int UID_MIN = 4;
    private final int USERCLASS_MIN=1;
    private final int GRADE_MIN=1;
    private final int GRADE_MAX=3;
    private final int NUMBER_MIN=1;
    private final int EMAIL_MAX=320;

    // 이메일 유효성 검사를 위한 정규 표현식
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // 비밀번호 유효성 검사를 위한 패턴. 최소 1개의 알파벳, 특수문자, 숫자가 포함되어야 함.
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[~!@#$%^&*+=()_-])(?=.*[0-9]).+$");

    /**
     * 이메일의 유효성을 검사합니다.
     *
     * @param email 검사할 이메일 주소
     * @return 이메일이 유효하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN)&&email.length()<EMAIL_MAX;
    }

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
        if (password == null || password.length() < PASSWORD_MIN || password.length() > PASSWORD_MAX) {
            return false;
        }
        if (!password.equals(rePassword)) {
            return false;
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return false;
        }
        if (password.contains(UID)) {
            return false;
        }
        return true;
    }

    /**
     * 사용자 ID의 유효성을 검사합니다.
     * ID는 최소 길이와 최대 길이 범위 내에 있어야 합니다.
     *
     * @param UID 검사할 사용자 ID
     * @return ID가 유효하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean isValidID(String UID) {
        return UID != null && UID.length() >= UID_MIN && UID.length() <= UID_MAX;
    }

    /**
     * 사용자 이름의 유효성을 검사합니다.
     * 이름은 최소 길이와 최대 길이 범위 내에 있어야 합니다.
     *
     * @param username 검사할 사용자 이름
     * @return 이름이 유효하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean isValidUsername(String username) {
        return username != null && username.length() >= NAME_MIN && username.length() <= NAME_MAX;
    }

    /**
     * 사용자 클래스의 유효성을 검사합니다.
     * 클래스는 1 이상이어야 합니다.
     *
     * @param userClass 검사할 사용자 클래스
     * @return 사용자 클래스가 유효하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean isValidUserClass(Long userClass) {
        return userClass != null && userClass >= USERCLASS_MIN;
    }

    /**
     * 학년의 유효성을 검사합니다.
     * 학년은 1 이상 3 이하이어야 합니다.
     *
     * @param grade 검사할 학년
     * @return 학년이 유효하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean isValidGrade(Long grade) {
        return grade != null && grade >= GRADE_MIN && grade <= GRADE_MAX;
    }

    /**
     * 번호의 유효성을 검사합니다.
     * 번호는 1 이상이어야 합니다.
     *
     * @param number 검사할 번호
     * @return 번호가 유효하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean isValidNumber(Long number) {
        return number != null && number >= NUMBER_MIN;
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
