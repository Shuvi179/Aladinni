package com.monesoft.refelin.security;

import com.monesoft.refelin.entity.Role;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SecurityRole {
    ROLE_HR(1L), ROLE_ADMIN(2L);

    private final Long id;

    SecurityRole(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public static Role getRole(SecurityRole securityRole) { return new Role(securityRole.getId(), securityRole.name()); }

    public static List<Role> getAllRoles() {
        return Arrays.stream(SecurityRole.values())
                .map(SecurityRole::getRole)
                .collect(Collectors.toList());
    }
}
