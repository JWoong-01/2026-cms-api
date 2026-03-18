package com.malgn.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @Schema(description = "로그인 사용자명", example = "user1")
    @NotBlank(message = "username is required")
    String username,
    @Schema(description = "로그인 비밀번호", example = "user1234")
    @NotBlank(message = "password is required")
    String password
) {
}
