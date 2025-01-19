package org.plteco.ploytechcourse.application.user.signup.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.signup.dto.EmailDto;
import org.plteco.ploytechcourse.domain.user.signup.service.SendEmailService;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.springframework.stereotype.Service;

/**
 * 이메일 전송을 처리하는 서비스 구현 클래스입니다.
 * <p>
 * 이 클래스는 이메일 주소가 유효한지 확인하고, 유효한 이메일일 경우
 * 인증번호를 이메일로 발송하는 기능을 제공합니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class SendEmailApplicationImpl implements SendEmailApplication {

    private final ValidationService validationService;  // 이메일 유효성 검사를 위한 서비스
    private final SendEmailService sendEmailService;    // 이메일로 인증코드를 전송하는 서비스

    /**
     * 주어진 이메일로 인증번호를 전송합니다.
     * <p>
     * 이메일이 유효하지 않으면 오류 메시지를 반환하고, 유효한 이메일에 대해서는
     * 인증번호를 이메일로 전송합니다.
     * </p>
     *
     * @param emailDto 이메일 전송에 필요한 정보가 담긴 DTO 객체
     * @return 이메일 전송 결과 메시지
     */
    @Override
    public String sendEmail(EmailDto emailDto) {
        // 이메일 유효성 검사
        if (!validationService.isValidEmail(emailDto.getEmail())) {
            return "이메일이 유효하지 않습니다."; // 이메일이 유효하지 않으면 오류 메시지 반환
        } else {
            // 이메일이 유효하면 인증번호 발송
            sendEmailService.sendCodeToEmail(emailDto.getEmail());
        }
        return "이메일로 인증번호를 발송했습니다."; // 인증번호 발송 성공 메시지 반환
    }
}
