package com.example.demo.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {
    private final String status = "error";
    protected String timestamp;
    protected Object message;

    public ExceptionResponse(Object message) {
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }
}
