package org.plteco.ploytechcourse.domain.user.signup.service;

import jakarta.mail.MessagingException;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.VerificationCode;

public interface SendEmailService {
    void sendCodeToEmail(String email);
    VerificationCode createVerificationCode(String email);
    boolean verifyCode(String email, String code);
    void sendEmail(String toEmail, String title, String content) throws MessagingException; // 예외 선언 추가
}

