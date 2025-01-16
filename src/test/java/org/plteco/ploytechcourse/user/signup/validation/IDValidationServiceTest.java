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

public class IDValidationServiceTest {


    private static final String SUCCESS_ID = "huhon";
    private static final String FAIL_ID = "hu";
    private static final String SHORT_ID = "hu";
    private static final String LONG_ID = "huhon123456789012345";

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
    @DisplayName("아이디 유효성 검사 성공")
    @Test
    public void isSuccessValidID() {
        boolean result = validationService.isValidID(SUCCESS_ID);
        assertTrue(result);
    }

    @Tag("validation_failure")
    @DisplayName("아이디 유효성 검사 실패")
    @Test
    public void isFailValidID() {
        boolean result = validationService.isValidID(FAIL_ID);
        assertFalse(result);
    }

    @Tag("boundary_test")
    @DisplayName("아이디 최소 길이 경계값 테스트")
    @Test
    public void testIDMinLength() {
        boolean result = validationService.isValidID(SHORT_ID);
        assertFalse(result);
    }

    @Tag("boundary_test")
    @DisplayName("아이디 최대 길이 경계값 테스트")
    @Test
    public void testIDMaxLength() {
        boolean result = validationService.isValidID(LONG_ID);
        assertFalse(result);
    }
}
