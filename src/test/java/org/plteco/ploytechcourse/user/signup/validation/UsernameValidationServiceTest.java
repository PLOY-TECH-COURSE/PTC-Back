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

public class UsernameValidationServiceTest {


    private static final String SUCCESS_NAME = "허온";
    private static final String FAIL_NAME = "허";
    private static final String SHORT_NAME = "허";
    private static final String LONG_NAME = "프라이인드로스테쭈젠댄마리소피아수인레나테엘리자벳피아루이제이";

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
    @DisplayName("이름 유효성 검사 성공")
    @Test
    public void isSuccessValidUserName() {
        boolean result = validationService.isValidUsername(SUCCESS_NAME);
        assertTrue(result);
    }

    @Tag("validation_failure")
    @DisplayName("이름 유효성 검사 실패")
    @Test
    public void isFailValidUserName() {
        boolean result = validationService.isValidUsername(FAIL_NAME);
        assertFalse(result);
    }

    @Tag("boundary_test")
    @DisplayName("이름 최소 길이 경계값 테스트")
    @Test
    public void testUsernameMinLength() {
        boolean result = validationService.isValidUsername(SHORT_NAME);
        assertFalse(result);
    }

    @Tag("boundary_test")
    @DisplayName("이름 최대 길이 경계값 테스트")
    @Test
    public void testUsernameMaxLength() {
        boolean result = validationService.isValidUsername(LONG_NAME);
        assertFalse(result);
    }
}
