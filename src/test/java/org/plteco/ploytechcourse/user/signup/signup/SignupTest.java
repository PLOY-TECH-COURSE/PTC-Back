package org.plteco.ploytechcourse.user.signup.signup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.plteco.ploytechcourse.domain.user.signup.service.ValidationImpl;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SignupTest {

    @BeforeEach
    public void setup() {
        validation = new ValidationImpl();
    }

    @Test
    public void testSignup() {

    }
}
