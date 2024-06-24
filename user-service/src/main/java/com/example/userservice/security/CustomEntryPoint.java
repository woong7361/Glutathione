package com.example.userservice.security;

import com.example.userservice.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class CustomEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("authentication fail");

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");


        PrintWriter writer = response.getWriter();
        response.setStatus(401);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(authException.getMessage())
                .build();

        writer.write(objectMapper.writeValueAsString(errorResponse));
        writer.flush();

    }
}