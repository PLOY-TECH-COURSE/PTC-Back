package org.plteco.ploytechcourse.application.user.repassword.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.repassword.dto.PasswordRequestDTO;
import org.plteco.ploytechcourse.application.user.signup.dto.EmailAndCodeDto;
import org.plteco.ploytechcourse.application.user.signup.dto.EmailDto;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.plteco.ploytechcourse.domain.user.signup.service.SendEmailService;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationService;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailValidationImpl implements EmailValidation {

    private final SendEmailService sendEmailService;
    private final ValidationService validationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    public void validate(EmailAndCodeDto emailAndCodeDto) {
        if (!validationService.verifyCode(emailAndCodeDto.getEmail(), emailAndCodeDto.getCode())) {
            throw new PltecoException("코드가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public void sendEmail(EmailDto emailDto) {
        if(!userRepository.existsByEmail(emailDto.getEmail())) {
            throw new PltecoException("이상한 이메일.", HttpStatus.BAD_REQUEST);
        }
        sendEmailService.sendCodeToEmail(emailDto.getEmail());
    }

    @Transactional
    @Override
    public void change(PasswordRequestDTO requestDTO) {
        User byEmail = userRepository.findByEmail(requestDTO.getEmail());
        byEmail.updatePassword(bCryptPasswordEncoder.encode(requestDTO.getPassword()));
    }
}
