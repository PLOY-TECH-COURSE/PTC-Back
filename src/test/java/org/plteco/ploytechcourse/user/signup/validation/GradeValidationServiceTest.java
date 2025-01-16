package org.plteco.ploytechcourse.user.signup.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class GradeValidationServiceTest {

    private ValidationService validationService;
    private static final Long VALID_GRADE = 2L;
    private static final Long INVALID_GRADE = 0L;

    @BeforeEach
    public void setup() {
        validationService = new ValidationServiceImpl();
    }

    @Tag("validation_success")
    @DisplayName("학년 유효성 검사 성공")
    @Test
    public void isSuccessValidGrade() {
        boolean result = validationService.isValidGrade(VALID_GRADE);
        assertTrue(result);
    }

    @Tag("validation_failure")
    @DisplayName("학년 유효성 검사 실패")
    @Test
    public void isFailValidGrade() {
        boolean result = validationService.isValidGrade(INVALID_GRADE);
        assertFalse(result);
    }
}
