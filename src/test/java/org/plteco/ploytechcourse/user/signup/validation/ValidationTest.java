package org.plteco.ploytechcourse.user.signup.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.plteco.ploytechcourse.domain.user.signup.service.Validation;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationImpl;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {
    // 성공하는 이메일
    final String SUCCESS_EMAIL = "email@email.com";
    // 실패하는 이메일
    final String FAIL_EMAIL = "email.com";
    // 성공하는 비밀번호
    final String SUCCESS_PASSWORD = "P@ssw0rd1009!";
    // 실패하는 비밀번호
    final String FAIL_PASSWORD = "password";
    // 성공하는 아이디
    final String SUCCESS_ID = "huhon";
    // 실패하는 아이디
    final String FAIL_ID = "hu";
    // 성공하는 이름
    final String SUCCESS_NAME = "허온";
    // 실패하는 이름
    final String FAIL_NAME = "허";
    // 비밀번호 경계값
    private static final String SHORT_PASSWORD = "P@ssw0";
    private static final String LONG_PASSWORD = "P@ssw0rd1234567890123456789012312321312312312321312!";
    // 아이디 경계값
    private static final String SHORT_ID = "hu";
    private static final String LONG_ID = "huhon123456789012345";
    // 이름 경계값
    private static final String SHORT_NAME = "허";
    private static final String LONG_NAME = "프라이인드로스테쭈젠댄마리소피아수인레나테엘리자벳피아루이제이";

    // validation 객체를 @BeforeEach로 초기화
    private Validation validation;

    @BeforeEach
    public void setup() {
        validation = new ValidationImpl();
    }

    @Tag("validation_success")
    @DisplayName("이메일 유효성 검사 성공")
    @Test
    public void isSuccessValidEmail() {
        boolean result = validation.isValidEmail(SUCCESS_EMAIL);
        assertTrue(result);
    }

    @Tag("validation_success")
    @DisplayName("비밀번호 유효성 검사 성공")
    @Test
    public void isSuccessValidPassword() {
        boolean result = validation.isValidPassword(SUCCESS_PASSWORD, SUCCESS_PASSWORD, SUCCESS_ID);
        assertTrue(result);
    }

    @Tag("validation_success")
    @DisplayName("아이디 유효성 검사 성공")
    @Test
    public void isSuccessValidID() {
        boolean result = validation.isValidID(SUCCESS_ID);
        assertTrue(result);
    }

    @Tag("validation_success")
    @DisplayName("이름 유효성 검사 성공")
    @Test
    public void isSuccessValidUserName() {
        boolean result = validation.isValidUsername(SUCCESS_NAME);
        assertTrue(result);
    }

    @Tag("validation_failure")
    @DisplayName("이메일 유효성 검사 실패")
    @Test
    public void isFailValidEmail() {
        boolean result = validation.isValidEmail(FAIL_EMAIL);
        assertFalse(result);
    }

    @Tag("validation_failure")
    @DisplayName("비밀번호 유효성 검사 실패")
    @Test
    public void isFailValidPassword() {
        boolean result = validation.isValidPassword(FAIL_PASSWORD, FAIL_PASSWORD, FAIL_ID);
        assertFalse(result);
    }

    @Tag("validation_failure")
    @DisplayName("비밀번호 확인 유효성 검사 실패")
    @Test
    public void isFailValidPasswordTwo() {
        boolean result = validation.isValidPassword(SUCCESS_PASSWORD, FAIL_PASSWORD, FAIL_ID);
        assertFalse(result);
    }

    @Tag("validation_failure")
    @DisplayName("아이디 유효성 검사 실패")
    @Test
    public void isFailValidID() {
        boolean result = validation.isValidID(FAIL_ID);
        assertFalse(result);
    }

    @Tag("validation_failure")
    @DisplayName("이름 유효성 검사 실패")
    @Test
    public void isFailValidUserName() {
        boolean result = validation.isValidUsername(FAIL_NAME);
        assertFalse(result);
    }

    @Tag("boundary_test")
    @DisplayName("비밀번호 최소 길이 경계값 테스트")
    @Test
    public void testPasswordMinLength() {
        boolean result = validation.isValidPassword(SHORT_PASSWORD, SHORT_PASSWORD, "huhon");
        assertFalse(result);
    }

    @Tag("boundary_test")
    @DisplayName("비밀번호 최대 길이 경계값 테스트")
    @Test
    public void testPasswordMaxLength() {
        boolean result = validation.isValidPassword(LONG_PASSWORD, LONG_PASSWORD, "huhon");
        assertFalse(result);
    }

    @Tag("boundary_test")
    @DisplayName("아이디 최소 길이 경계값 테스트")
    @Test
    public void testIDMinLength() {
        boolean result = validation.isValidID(SHORT_ID);
        assertFalse(result);
    }

    @Tag("boundary_test")
    @DisplayName("아이디 최대 길이 경계값 테스트")
    @Test
    public void testIDMaxLength() {
        boolean result = validation.isValidID(LONG_ID);
        assertFalse(result);
    }

    @Tag("boundary_test")
    @DisplayName("이름 최소 길이 경계값 테스트")
    @Test
    public void testUsernameMinLength() {
        boolean result = validation.isValidUsername(SHORT_NAME);
        assertFalse(result);
    }

    @Tag("boundary_test")
    @DisplayName("이름 최대 길이 경계값 테스트")
    @Test
    public void testUsernameMaxLength() {
        boolean result = validation.isValidUsername(LONG_NAME);
        assertFalse(result);
    }
}
