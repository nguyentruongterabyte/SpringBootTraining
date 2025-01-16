package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestResponse {
    private final String status = "success";
    private String timestamp;
    private String message;
    private Object data;

    public RequestResponse(Object data) {
        this.timestamp = LocalDateTime.now().toString();
        this.data = data;
        this.message = "";
    }

    public RequestResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
        this.data = null;
    }
}
