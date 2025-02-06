package org.plteco.ploytechcourse.domain.user.signup.service;

public interface ValidationService {
    boolean isValidPassword(String password,String rePassword,String ID);
    boolean verifyCode(String email, String code);
}
