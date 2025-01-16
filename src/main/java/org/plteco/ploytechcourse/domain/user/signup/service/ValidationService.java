package org.plteco.ploytechcourse.domain.user.signup.service;

public interface ValidationService {
    boolean isValidEmail(String email);
    boolean isValidPassword(String password,String rePassword,String ID);
    boolean isValidID(String ID);
    boolean isValidUsername(String username);
    boolean isValidUserClass(Long userClass);
    boolean isValidGrade(Long grade);
    boolean isValidNumber(Long number);
    boolean verifyCode(String email, String code);
}
