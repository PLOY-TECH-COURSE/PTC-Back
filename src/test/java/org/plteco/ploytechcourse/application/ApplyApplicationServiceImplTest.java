package org.plteco.ploytechcourse.application;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.plteco.ploytechcourse.application.application.dto.ApplyApplicationDto;
import org.plteco.ploytechcourse.domain.application.model.TechCourseForm;
import org.plteco.ploytechcourse.domain.application.repository.ApplicationRepository;
import org.plteco.ploytechcourse.domain.application.service.ApplyApplicationServiceImpl;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplyApplicationServiceImplTest {

    @Mock
    private UserContextUtil userContextUtil;

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplyApplicationServiceImpl applyApplicationService;

    private User mockUser;
    private ApplyApplicationDto mockDto;

    @BeforeEach
    void setUp() {
        mockUser = User.builder()
                .uid("test")
                .name("허조")
                .email("24.050@bssm.hs.kr")
                .password("qwe123")
                .role(RoleEnum.ROLE_USER)
                .grade(1L)
                .classNumber(4L)
                .number(15L)
                .build();

        mockDto = new ApplyApplicationDto("허온 바보", "조잼 오줌싸개");
    }

    @Test
    void testApplyApplicationSuccess() {
        // given
        when(userContextUtil.getCurrentUser()).thenReturn(mockUser);
        when(applicationRepository.save(any(TechCourseForm.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        TechCourseForm result = applyApplicationService.apply(mockDto);

        // then
        assertNotNull(result);
        assertEquals(mockUser, result.getUser());
        assertEquals("허온 바보", result.getIntroduction());
        assertEquals("조잼 오줌싸개", result.getResolution());

        // verify interactions
        verify(userContextUtil, times(1)).getCurrentUser();
        verify(applicationRepository, times(1)).save(any(TechCourseForm.class));
    }

    @Test
    void testIsValidDuplicationStudent_NoDuplicate() {
        // given
        Long userId = 1L;
        when(applicationRepository.existsByUserId(userId)).thenReturn(false);

        // when
        boolean isValid = applyApplicationService.isValidDuplicationStudent(userId);

        // then
        assertTrue(isValid);
        verify(applicationRepository, times(1)).existsByUserId(userId);
    }

    @Test
    void testIsValidDuplicationStudent_DuplicateExists() {
        // given
        Long userId = 1L;
        when(applicationRepository.existsByUserId(userId)).thenReturn(true);

        // when
        boolean isValid = applyApplicationService.isValidDuplicationStudent(userId);

        // then
        assertFalse(isValid);
        verify(applicationRepository, times(1)).existsByUserId(userId);
    }

    @Test
    void testIsValidIntroduction_ValidLength() {
        // when
        boolean isValid = applyApplicationService.isValidIntroduction("허동운은 신이다.");

        // then
        assertTrue(isValid);
    }

    @Test
    void testIsValidIntroduction_TooLong() {
        // given
        String longIntro = "엌".repeat(501);

        // when
        boolean isValid = applyApplicationService.isValidIntroduction(longIntro);

        // then
        assertFalse(isValid);
    }

    @Test
    void testIsValidResolution_ValidLength() {
        // when
        boolean isValid = applyApplicationService.isValidResolution("소가죽으면 ? 다이소ㅋㅋㅋㅋ");

        // then
        assertTrue(isValid);
    }

    @Test
    void testIsValidResolution_TooLong() {
        // given
        String longResolution = "엌".repeat(501);

        // when
        boolean isValid = applyApplicationService.isValidResolution(longResolution);

        // then
        assertFalse(isValid);
    }

    @Test
    void testIsValidResolution_null(){
        //when
        boolean isValid = applyApplicationService.isValidResolution(null);

        //then
        assertFalse(isValid);
    }

    @Test
    void testIsValidResolution_Empty(){
        //when
        boolean isValid = applyApplicationService.isValidResolution("");

        //then
        assertFalse(isValid);
    }

    @Test
    void testIsValidIntroduction_null(){
        //when
        boolean isValid = applyApplicationService.isValidResolution(null);

        //then
        assertFalse(isValid);
    }

    @Test
    void testIsValidIntroduction_Empty(){
        //when
        boolean isValid = applyApplicationService.isValidResolution("");

        //then
        assertFalse(isValid);
    }
}