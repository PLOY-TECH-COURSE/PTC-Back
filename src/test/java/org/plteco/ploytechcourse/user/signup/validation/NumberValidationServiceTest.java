package org.plteco.ploytechcourse.user.signup.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class NumberValidationServiceTest {

    private ValidationService validationService;
    private static final Long VALID_NUMBER = 10L;
    private static final Long INVALID_NUMBER = -1L;

    @BeforeEach
    public void setup() {
        validationService = new ValidationServiceImpl();
    }

    @Tag("validation_success")
    @DisplayName("번호 유효성 검사 성공")
    @Test
    public void isSuccessValidNumber() {
        boolean result = validationService.isValidNumber(VALID_NUMBER);
        assertTrue(result);
    }

    @Tag("validation_failure")
    @DisplayName("번호 유효성 검사 실패")
    @Test
    public void isFailValidNumber() {
        boolean result = validationService.isValidNumber(INVALID_NUMBER);
        assertFalse(result);
    }
}
