package com.monesoft.refelin.init;

import com.monesoft.refelin.repository.RoleRepository;
import com.monesoft.refelin.security.SecurityRole;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SecurityRoleLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;

    public SecurityRoleLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!roleRepository.existsById(1L)) {
            roleRepository.saveAll(SecurityRole.getAllRoles());
        }
    }
}
