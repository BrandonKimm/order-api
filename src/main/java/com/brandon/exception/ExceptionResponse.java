package com.brandon.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private LocalDateTime timestamp;
    private String message;
    private String path;
}