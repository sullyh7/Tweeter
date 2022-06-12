package com.sulay.tweeter.exception;

import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class ExceptionResponse {
    private String status;
    private String message;
    private Instant date;
}
