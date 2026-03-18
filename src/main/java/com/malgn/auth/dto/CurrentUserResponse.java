package com.malgn.auth.dto;

import com.malgn.common.security.SecurityUser;

import io.swagger.v3.oas.annotations.media.Schema;

public record CurrentUserResponse(
    @Schema(description = "사용자 ID", example = "1")
    Long id,
    @Schema(description = "사용자명", example = "user1")
    String username,
    @Schema(description = "권한", example = "USER")
    String role
) {

    public static CurrentUserResponse from(SecurityUser securityUser) {
        return new CurrentUserResponse(
            securityUser.id(),
            securityUser.username(),
            securityUser.role().name()
        );
    }
}
