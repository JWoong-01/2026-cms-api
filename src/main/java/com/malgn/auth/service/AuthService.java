package com.malgn.auth.service;

import com.malgn.auth.dto.CurrentUserResponse;
import com.malgn.auth.dto.LoginRequest;
import com.malgn.auth.dto.LoginResponse;
import com.malgn.common.exception.InvalidCredentialsException;
import com.malgn.common.security.CurrentUserAccessor;
import com.malgn.common.security.JwtProperties;
import com.malgn.common.security.JwtTokenProvider;
import com.malgn.common.security.SecurityUser;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            String accessToken = jwtTokenProvider.generateAccessToken(securityUser);

            return new LoginResponse(accessToken, "Bearer", jwtProperties.accessTokenExpirationSeconds());
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Invalid username or password");
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException("Authentication failed");
        }
    }

    public CurrentUserResponse getCurrentUser() {
        return CurrentUserResponse.from(CurrentUserAccessor.getCurrentUser());
    }
}
