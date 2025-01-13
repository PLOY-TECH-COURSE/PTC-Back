package org.plteco.ploytechcourse.domain.user.signup.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * 사용자 등록 관련 유효성 검사를 처리하는 클래스입니다.
 * 이메일, 비밀번호, ID, 사용자 이름에 대한 유효성 검사를 담당합니다.
 */
@Service
public class ValidationImpl implements Validation {

    // 비밀번호의 최대 길이 제한
    private static final int PASSWORD_MAX = 30;

    // 비밀번호의 최소 길이 제한
    private static final int PASSWORD_MIN = 10;

    //이름의 최대 길이 제한
    private static final int NAME_MAX = 30;

    //이름의 최소 길이 제한
    private static final int NAME_MIN = 2;

    //아이디의 최대 길이 제한
    private static final int ID_MAX = 15;

    //아이디의 최소길이 제한
    private static final int ID_MIN = 4;

    // 이메일 유효성 검사를 위한 정규 표현식
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // 비밀번호 유효성 검사를 위한 패턴. 최소 1개의 알파벳, 특수문자, 숫자가 포함되어야 함.
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[~!@#$%^&*+=()_-])(?=.*[0-9]).+$");


    /**
     * 이메일 주소가 유효한지 검사하는 메서드입니다.
     * 이메일 주소가 null이 아니고, 정규 표현식에 맞는지 확인합니다.
     *
     * @param email 이메일 주소
     * @return 이메일이 유효하면 true, 그렇지 않으면 false를 반환합니다.
     */
    @Override
    public boolean isValidEmail(String email) {
        // 이메일 주소가 null이 아닌지 확인하고, 정규 표현식에 맞는지 검사
        return email != null && email.matches(EMAIL_PATTERN);
    }

    /**
     * 비밀번호의 유효성을 검사하는 메서드입니다.
     * 비밀번호가 null이 아니고, 길이가 최소 MIN 이상, 최대 MAX 이하인지 검사합니다.
     * 또한, 비밀번호는 숫자, 알파벳, 특수문자가 포함되어야 하며, 비밀번호에 ID가 포함되지 않아야 합니다.
     *
     * @param password 사용자가 입력한 비밀번호
     * @param ID 사용자의 ID (비밀번호에 포함되지 않아야 함)
     * @return 비밀번호가 유효하면 true, 그렇지 않으면 false를 반환합니다.
     */
    @Override
    public boolean isValidPassword(String password, String rePassword, String ID) {
        // 비밀번호가 null이거나 길이가 유효하지 않으면 false 반환
        if (password == null || password.length() <= PASSWORD_MIN || password.length() >= PASSWORD_MAX) {
            return false;
        }
        // 비밀번호와 확인용 비밀번호가 일치하지 않으면 false 반환
        if (!password.equals(rePassword)) {
            return false;
        }
        // 비밀번호가 정규 표현식에 맞지 않으면 false 반환
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return false;
        }
        // 비밀번호에 ID가 포함되면 false 반환
        return !password.contains(ID);
    }


    /**
     * 사용자 ID의 유효성을 검사하는 메서드입니다.
     * ID가 null이 아니고, 길이가 최소 MIN 이상, 최대 MAX 이하인지 확인합니다.
     *
     * @param ID 사용자의 ID
     * @return ID가 유효하면 true, 그렇지 않으면 false를 반환합니다.
     */
    @Override
    public boolean isValidID(String ID) {
        // ID가 null이 아니고, 길이가 유효한 범위에 있는지 확인
        return ID != null && ID.length() >= ID_MIN && ID.length() <= ID_MAX;
    }

    /**
     * 사용자 이름의 유효성을 검사하는 메서드입니다.
     * 사용자 이름이 null이 아니고, 길이가 최소 MIN 이상, 최대 MAX 이하인지 확인합니다.
     *
     * @param username 사용자의 이름
     * @return 사용자 이름이 유효하면 true, 그렇지 않으면 false를 반환합니다.
     */
    @Override
    public boolean isValidUsername(String username) {
        // 사용자 이름이 null이 아니고, 길이가 유효한 범위에 있는지 확인
        return username != null && username.length() >= NAME_MIN && username.length() <= NAME_MAX;
    }
}
