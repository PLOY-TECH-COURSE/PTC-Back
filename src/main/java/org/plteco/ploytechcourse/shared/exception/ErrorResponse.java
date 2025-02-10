package org.plteco.ploytechcourse.shared.exception;

import java.time.LocalDateTime;

// record가 클래스의 생성자, Getter, toString등을 자동으로 만들어준다.
public record ErrorResponse(
        int status,
        String errorCode,
        String message,
        LocalDateTime timestamp
) {
    // from은 정적 팩토리 메서드이다.
    public static ErrorResponse from(PltecoException ex) {
        return new ErrorResponse(
                ex.getStatus().value(),
                ex.getMessage(),
                ex.getStatus().getReasonPhrase(),   // status의 reasonPhrase에 "URI Too Long"과 같은 짧은 설명이 있다.
                LocalDateTime.now()
        );
    }

    public static ErrorResponse from(int status, String errorCode, String message) {
        return new ErrorResponse(
                status,
                errorCode,
                message,
                LocalDateTime.now()
        );
    }
}