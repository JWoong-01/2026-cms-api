package com.malgn.common.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.HttpStatus;

public record ApiErrorResponse(
    @Schema(description = "HTTP 상태 코드", example = "403")
    int status,
    @Schema(description = "HTTP 에러명", example = "Forbidden")
    String error,
    @Schema(description = "에러 메시지", example = "Access denied")
    String message,
    @Schema(description = "에러 발생 시각")
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
