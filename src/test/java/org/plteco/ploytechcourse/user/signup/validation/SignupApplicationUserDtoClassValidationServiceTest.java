package org.plteco.ploytechcourse.user.signup.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.plteco.ploytechcourse.domain.user.signup.repository.VerificationCodeRepository;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class SignupApplicationUserDtoClassValidationServiceTest {


    private static final Long VALID_USER_CLASS = 1L;
    private static final Long INVALID_USER_CLASS = -1L;

    @Mock
    private VerificationCodeRepository verificationCodeRepository;

    @InjectMocks
    private ValidationServiceImpl validationService;

    @BeforeEach
    public void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }
    @Tag("validation_success")
    @DisplayName("학급 유효성 검사 성공")
    @Test
    public void isSuccessValidUserClass() {
        boolean result = validationService.isValidUserClass(VALID_USER_CLASS);
        assertTrue(result);
    }

    @Tag("validation_failure")
    @DisplayName("학급 유효성 검사 실패")
    @Test
    public void isFailValidUserClass() {
        boolean result = validationService.isValidUserClass(INVALID_USER_CLASS);
        assertFalse(result);
    }
}
