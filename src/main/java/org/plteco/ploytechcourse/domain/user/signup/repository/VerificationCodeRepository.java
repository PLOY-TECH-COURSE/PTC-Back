package org.plteco.ploytechcourse.domain.user.signup.repository;


import org.plteco.ploytechcourse.domain.user.signup.model.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByEmailAndCode(String email, String code);
}
