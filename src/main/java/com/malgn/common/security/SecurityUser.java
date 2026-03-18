package com.malgn.common.security;

import java.util.Collection;
import java.util.List;

import com.malgn.user.domain.User;
import com.malgn.user.domain.UserRole;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record SecurityUser(
    Long id,
    String username,
    String password,
    UserRole role,
    boolean enabled
) implements UserDetails {

    public static SecurityUser from(User user) {
        return new SecurityUser(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            user.getRole(),
            user.isEnabled()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.authority()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
