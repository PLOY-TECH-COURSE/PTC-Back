package org.plteco.ploytechcourse.shared.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // JSON 수동 작성
        String jsonResponse = "{"
                + "\"timestamp\":\"" + LocalDateTime.now() + "\","
                + "\"status\":403,"
                + "\"error\":\"Forbidden\","
                + "\"message\":\"" + accessDeniedException.getMessage() + "\","
                + "\"path\":\"" + request.getRequestURI() + "\""
                + "}";

        PrintWriter writer = response.getWriter();
        writer.write(jsonResponse);
        writer.flush();

    }
}

