package org.plteco.ploytechcourse.shared.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor    // 상태코드만 받는 생성자 생성
public class PltecoException extends RuntimeException {
    private final HttpStatus status;    // 상태코드

    public PltecoException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
