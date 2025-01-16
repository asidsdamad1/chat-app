package org.asi.authservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUserDetailsImpl implements UserDetails {

    private final String username;
    private final String password;
    private final String id;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    public SecurityUserDetailsImpl(String id, String username, String password, boolean enabled, List<SimpleGrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    public SecurityUserDetailsImpl(String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.authorities = authorities;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

}
