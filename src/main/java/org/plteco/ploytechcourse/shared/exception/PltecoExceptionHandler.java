package org.plteco.ploytechcourse.shared.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PltecoExceptionHandler {
    @ExceptionHandler(value = PltecoException.class)
    public ResponseEntity<ErrorResponse> handlePltecoException(PltecoException ex) {
        ErrorResponse errorResponse = ErrorResponse.from(ex);
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    // 유효성 에러
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> ExceptionHandler(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = ErrorResponse.from(400, "INVALID_ARGUMENT", ex.getMessage());
        return ResponseEntity.status(400).body(errorResponse);
    }

    // 로그인 실패
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ErrorResponse> ExceptionHandler(AuthenticationException ex) {
        ErrorResponse errorResponse = ErrorResponse.from(400, "INVALID_CREDENTIALS", ex.getMessage());
        return ResponseEntity.status(400).body(errorResponse);
    }

    // Null
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ErrorResponse> ExceptionHandler(NullPointerException ex) {
        ErrorResponse errorResponse = ErrorResponse.from(400, "NULL_POINTER_EXCEPTION", ex.getMessage());
        return ResponseEntity.status(400).body(errorResponse);
    }

    // 권한 없음
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> ExceptionHandler(AccessDeniedException ex) {
        ErrorResponse errorResponse = ErrorResponse.from(403, "ACCESS_DENIED", ex.getMessage());
        return ResponseEntity.status(403).body(errorResponse);
    }

    // 데이터베이스 오류
    @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity<ErrorResponse> ExceptionHandler(DataAccessException ex) {
        ErrorResponse errorResponse = ErrorResponse.from(500, "DATABASE_ERROR", ex.getMessage());
        return ResponseEntity.status(500).body(errorResponse);
    }

    // 나머지 에러
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handlePltecoException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.from(500, "SERVER_UNKNOWN", ex.getMessage());
        return ResponseEntity.status(500).body(errorResponse);
    }
}
