package com.malgn.auth.dto;

import com.malgn.common.security.SecurityUser;

public record CurrentUserResponse(
    Long id,
    String username,
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
