package org.plteco.ploytechcourse.user.signup.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class EmailValidationServiceTest {

    private ValidationService validationService;
    private static final String SUCCESS_EMAIL = "email@email.com";
    private static final String FAIL_EMAIL = "email.com";

    @BeforeEach
    public void setup() {
        validationService = new ValidationServiceImpl();
    }

    @Tag("validation_success")
    @DisplayName("이메일 유효성 검사 성공")
    @Test
    public void isSuccessValidEmail() {
        boolean result = validationService.isValidEmail(SUCCESS_EMAIL);
        assertTrue(result);
    }

    @Tag("validation_failure")
    @DisplayName("이메일 유효성 검사 실패")
    @Test
    public void isFailValidEmail() {
        boolean result = validationService.isValidEmail(FAIL_EMAIL);
        assertFalse(result);
    }
}
