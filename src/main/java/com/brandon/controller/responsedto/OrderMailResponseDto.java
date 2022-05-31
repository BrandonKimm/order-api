package com.brandon.controller.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderMailResponseDto {

    private String email;
    private String subject;
    private String message;
}
