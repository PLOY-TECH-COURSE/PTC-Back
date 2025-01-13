package org.plteco.ploytechcourse.domain.user.signup.service;

public interface Validation {
    boolean isValidEmail(String email);
    boolean isValidPassword(String password,String rePassword,String ID);
    boolean isValidID(String ID);
    boolean isValidUsername(String username);
}
