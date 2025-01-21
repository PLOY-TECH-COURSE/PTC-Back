package org.plteco.ploytechcourse.user.signup.signup;


import org.junit.jupiter.api.Test;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 테스트 종료 후 DB 상태를 롤백
public class SignupTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        // 테스트할 사용자 객체 생성
        User user = User.builder()
                .uid("test123")
                .email("test@example.com")
                .name("테스트")
                .password("encodedPassword")
                .role(RoleEnum.ROLE_USER)
                .grade(1L)
                .classNumber(2L)
                .number(3L)
                .build();

        // 데이터베이스에 저장
        userRepository.save(user);

        // 저장 여부 확인
        assertTrue(userRepository.existsByEmail("test@example.com"));
    }
}
