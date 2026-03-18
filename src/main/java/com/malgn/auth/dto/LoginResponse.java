package com.malgn.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
    @Schema(description = "JWT access token")
    String accessToken,
    @Schema(description = "토큰 타입", example = "Bearer")
    String tokenType,
    @Schema(description = "토큰 만료 시간(초)", example = "3600")
    long expiresIn
) {
}
