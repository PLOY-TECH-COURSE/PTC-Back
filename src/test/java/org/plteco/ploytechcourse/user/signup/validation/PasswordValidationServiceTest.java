package org.plteco.ploytechcourse.user.signup.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordValidationServiceTest {

    private ValidationService validationService;
    private static final String SUCCESS_PASSWORD = "P@ssw0rd1009!";
    private static final String FAIL_PASSWORD = "password";
    private static final String SHORT_PASSWORD = "P@ssw0";
    private static final String LONG_PASSWORD = "P@ssw0rd1234567890123456789012312321312312312321312!";
    private static final String SUCCESS_ID = "huhon";

    @BeforeEach
    public void setup() {
        validationService = new ValidationServiceImpl();
    }

    @Tag("validation_success")
    @DisplayName("비밀번호 유효성 검사 성공")
    @Test
    public void isSuccessValidPassword() {
        boolean result = validationService.isValidPassword(SUCCESS_PASSWORD, SUCCESS_PASSWORD, SUCCESS_ID);
        assertTrue(result);
    }

    @Tag("validation_failure")
    @DisplayName("비밀번호 유효성 검사 실패")
    @Test
    public void isFailValidPassword() {
        boolean result = validationService.isValidPassword(FAIL_PASSWORD, FAIL_PASSWORD, SUCCESS_ID);
        assertFalse(result);
    }

    @Tag("validation_failure")
    @DisplayName("비밀번호 확인 유효성 검사 실패")
    @Test
    public void isFailValidPasswordTwo() {
        boolean result = validationService.isValidPassword(SUCCESS_PASSWORD, FAIL_PASSWORD, SUCCESS_ID);
        assertFalse(result);
    }

    @Tag("boundary_test")
    @DisplayName("비밀번호 최소 길이 경계값 테스트")
    @Test
    public void testPasswordMinLength() {
        boolean result = validationService.isValidPassword(SHORT_PASSWORD, SHORT_PASSWORD, SUCCESS_ID);
        assertFalse(result);
    }

    @Tag("boundary_test")
    @DisplayName("비밀번호 최대 길이 경계값 테스트")
    @Test
    public void testPasswordMaxLength() {
        boolean result = validationService.isValidPassword(LONG_PASSWORD, LONG_PASSWORD, SUCCESS_ID);
        assertFalse(result);
    }
}
