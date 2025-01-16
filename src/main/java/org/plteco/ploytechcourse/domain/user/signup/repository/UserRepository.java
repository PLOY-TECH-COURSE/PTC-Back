package org.plteco.ploytechcourse.domain.user.signup.repository;

import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUid(String uid);

}
