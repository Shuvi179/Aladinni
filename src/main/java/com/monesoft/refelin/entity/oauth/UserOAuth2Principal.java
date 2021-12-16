package com.monesoft.refelin.entity.oauth;

import com.monesoft.refelin.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserOAuth2Principal implements OAuth2User {

    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes= new HashMap<>();

    public UserOAuth2Principal(User user) {
        this.email = user.getEmail();
        this.authorities = user.getAuthorities();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return email;
    }
}
