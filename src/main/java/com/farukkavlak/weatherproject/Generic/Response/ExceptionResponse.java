package com.farukkavlak.weatherproject.Generic.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private Date errorDate;
    private String message;
    private String detail;
}