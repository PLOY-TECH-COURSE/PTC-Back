package org.plteco.ploytechcourse.domain.user.signup.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.VerificationCode;
import org.plteco.ploytechcourse.domain.user.signup.repository.VerificationCodeRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 이메일을 통해 인증 코드를 전송하고, 해당 코드를 검증하는 서비스입니다.
 * 이 서비스는 이메일 발송, 인증 코드 생성, 코드 검증 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class SendEmailServiceImpl implements SendEmailService {

    private final JavaMailSender emailSender;
    private final VerificationCodeRepository verificationCodeRepository;

    /**
     * 주어진 이메일 주소로 인증 코드를 전송합니다.
     *
     * @param email 인증 코드를 받을 이메일 주소
     * @throws RuntimeException 이메일 전송 실패 시 예외 발생
     */
    @Override
    public void sendCodeToEmail(String email) {
        VerificationCode createdCode = createVerificationCode(email);
        String title = "플테코 이메일 인증 번호";

        String content = "<html>"
                + "<body>"
                + "<h1>플테코 인증 코드: " + createdCode.getCode() + "</h1>"
                + "<p>해당 코드를 홈페이지에 입력하세요.</p>"
                + "<footer style='color: grey; font-size: small;'>"
                + "<p>※본 메일은 자동응답 메일이므로 본 메일에 회신하지 마시기 바랍니다.</p>"
                + "</footer>"
                + "</body>"
                + "</html>";
        try {
            sendEmail(email, title, content);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to send email in sendCodeToEmail", e);
        }
    }

    /**
     * 주어진 이메일에 대한 인증 코드를 생성하고 저장합니다.
     * 생성된 인증 코드는 10분 후에 만료됩니다.
     *
     * @param email 인증 코드를 생성할 이메일 주소
     * @return 생성된 인증 코드 객체
     */
    @Override
    public VerificationCode createVerificationCode(String email) {
        String randomCode = generateRandomCode(6);
        VerificationCode code = new VerificationCode(email, randomCode, LocalDateTime.now().plusMinutes(10)); // 만료 시간을 10분으로 설정
        return verificationCodeRepository.save(code);
    }

    /**
     * 이메일과 인증 코드가 일치하는지 확인하고, 코드가 만료되지 않았는지 검사합니다.
     *
     * @param email 인증을 확인할 이메일 주소
     * @param code 인증 코드
     * @return 코드가 유효하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean verifyCode(String email, String code) {
        return verificationCodeRepository.findByEmailAndCode(email, code)
                .map(vc -> vc.getExpiresTime().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    /**
     * 주어진 이메일 주소로 이메일을 전송합니다.
     *
     * @param toEmail 수신자 이메일 주소
     * @param title 이메일 제목
     * @param content 이메일 내용 (HTML 형식)
     * @throws MessagingException 이메일 전송 중 예외 발생 시
     */
    @Override
    public void sendEmail(String toEmail, String title, String content) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject(title);
        helper.setText(content, true);
        helper.setReplyTo("imgforestmail@gmail.com");
        emailSender.send(message);
    }

    /**
     * 주어진 길이로 랜덤한 인증 코드를 생성합니다.
     *
     * @param length 생성할 인증 코드의 길이
     * @return 생성된 랜덤 인증 코드
     */
    public String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
}
