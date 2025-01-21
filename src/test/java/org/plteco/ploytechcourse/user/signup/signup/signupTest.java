package org.plteco.ploytechcourse.user.signup.signup;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.plteco.ploytechcourse.application.user.signup.dto.SignupUserDto;
import org.plteco.ploytechcourse.application.user.signup.service.SignupApplicationImpl;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class signupTest {
    @Mock
    private ValidationService validationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private SignupApplicationImpl signupApplication;

    private SignupUserDto MockSignupUserDto;

    @BeforeEach
    void setUp() {
            MockSignupUserDto = SignupUserDto.builder()
                    .uid("heodongun")
                    .name("허온")
                    .email("heodongun@gmail.com")
                    .code("code")
                    .password("password")
                    .rePassword("password")
                    .bio("나는 사람이올시다")
                    .grade(1L)
                    .userClass(4L)
                    .number(14L)
                    .build();
    }

    @Test
    void existsByEmailSuccess() {
        //given
        when(userRepository.existsByEmail(MockSignupUserDto.getEmail())).thenReturn(false);

        //when
        String result=signupApplication.signup(MockSignupUserDto);

        //then
        Assertions.assertThat(result).isEqualTo("유효성 검사 성공");
    }

//    @Test
//    void existsByEmailFail() {
//        //given
//        when(userRepository.existsByEmail(MockSignupUserDto.getEmail())).thenReturn(true);
//
//        //when
//        String result=signupApplication.signup(MockSignupUserDto);
//
//
//    }
//
//    @Test
//    void existsByUidSuccess() {
//
//    }
//
//    @Test
//    void existsByUidFail() {
//
//    }
//
//    @Test
//    void signupSuccess() {
//
//    }
}
