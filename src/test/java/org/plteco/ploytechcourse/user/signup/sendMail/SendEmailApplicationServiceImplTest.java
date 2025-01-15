package org.plteco.ploytechcourse.user.signup.sendMail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.VerificationCode;
import org.plteco.ploytechcourse.domain.user.signup.repository.VerificationCodeRepository;
import org.plteco.ploytechcourse.domain.user.signup.service.SendEmailServiceImpl;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SendEmailApplicationServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private VerificationCodeRepository verificationCodeRepository;

    @InjectMocks
    private SendEmailServiceImpl sendMail;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendCodeToEmailTest() throws MessagingException {
        // Mock MimeMessage
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Mock VerificationCodeRepository
        when(verificationCodeRepository.save(any())).thenReturn(new VerificationCode("test@example.com", "123456", LocalDateTime.now().plusMinutes(10)));

        // Call the method
        sendMail.sendCodeToEmail("test@example.com");

        // Verify the email was sent
        verify(javaMailSender).send(mimeMessage);
    }
}
