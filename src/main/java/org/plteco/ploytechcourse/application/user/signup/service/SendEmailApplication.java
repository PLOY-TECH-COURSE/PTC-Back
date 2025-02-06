package org.plteco.ploytechcourse.application.user.signup.service;

import org.plteco.ploytechcourse.application.user.signup.dto.EmailDto;
import org.plteco.ploytechcourse.shared.exception.PltecoException;

/**
 * 이메일 전송을 처리하는 서비스 인터페이스입니다.
 * <p>
 * 이 인터페이스는 이메일 전송을 위한 메서드를 정의하고 있습니다.
 * 구현 클래스에서 실제 이메일 전송 로직을 구현합니다.
 * </p>
 */
public interface SendEmailApplication {

    /**
     * 주어진 이메일 정보를 바탕으로 이메일을 전송합니다.
     * <p>
     * 이메일 전송 기능을 구현하는 메서드로, 인증 코드나 기타 정보를 이메일로 발송하는 데 사용됩니다.
     * </p>
     *
     * @param emailDto 이메일 전송에 필요한 정보가 담긴 DTO 객체
     * @return 이메일 전송 결과 메시지
     */
    void sendEmail(EmailDto emailDto);
}
