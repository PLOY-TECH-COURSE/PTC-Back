package org.plteco.ploytechcourse.api.user.signup;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.signup.service.SendEmailApplication;
import org.plteco.ploytechcourse.application.user.signup.service.SignupApplication;
import org.plteco.ploytechcourse.application.user.signup.dto.SignupUserDto;
import org.plteco.ploytechcourse.application.user.signup.dto.EmailDto;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 회원가입 및 이메일 전송 API를 처리하는 컨트롤러입니다.
 */
@RestController
@RequiredArgsConstructor
public class SignupController {

    private final SignupApplication signupApplication;
    private final SendEmailApplication sendEmailApplication;

    /**
     * 사용자가 입력한 정보로 회원가입을 진행합니다.
     * <p>
     * 클라이언트에서 전달한 회원가입 정보(DTO)를 사용해 회원가입을 처리하고, 결과를 반환합니다.
     * </p>
     *
     * @param signupUserDto 회원가입에 필요한 정보가 담긴 DTO 객체
     * @return 회원가입 처리 결과 메시지
     */
    @PostMapping("/signup")
    public ResponseEntity<String>  signup(@Valid @RequestBody SignupUserDto signupUserDto) {
        signupApplication.signup(signupUserDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    /**
     * 사용자의 이메일로 인증 메일을 전송합니다.
     * <p>
     * 클라이언트에서 전달한 이메일 정보를 바탕으로 이메일 인증 메일을 발송합니다.
     * </p>
     *
     * @param emailDto 이메일 인증에 필요한 정보가 담긴 DTO 객체
     * @return 이메일 전송 결과 메시지
     */
    @PostMapping("/email")
    public ResponseEntity<String>  mail(@Valid @RequestBody EmailDto emailDto) {
        sendEmailApplication.sendEmail(emailDto);
        return ResponseEntity.ok("이메일을 보냈습니다.");
    }
}
