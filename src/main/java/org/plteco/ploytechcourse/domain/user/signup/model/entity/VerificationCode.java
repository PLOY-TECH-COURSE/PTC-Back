package org.plteco.ploytechcourse.domain.user.signup.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String code;
    private LocalDateTime expiresTime;

    @Builder
    public VerificationCode(String email, String code, LocalDateTime expiresTime) {
        this.email = email;
        this.code = code;
        this.expiresTime = expiresTime;
    }
}

