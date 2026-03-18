package com.malgn.common.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public record ApiErrorResponse(
    int status,
    String error,
    String message,
    LocalDateTime timestamp
) {

    public static ApiErrorResponse of(HttpStatus httpStatus, String message) {
        return new ApiErrorResponse(
            httpStatus.value(),
            httpStatus.getReasonPhrase(),
            message,
            LocalDateTime.now()
        );
    }
}
