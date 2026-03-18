package com.malgn.common.security;

import com.malgn.common.exception.UnauthorizedException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CurrentUserAccessor {

    public static SecurityUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
            || authentication instanceof AnonymousAuthenticationToken
            || !(authentication.getPrincipal() instanceof SecurityUser user)) {
            throw new UnauthorizedException("Authentication required");
        }

        return user;
    }
}
