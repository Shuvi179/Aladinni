package com.monesoft.refelin.service.contract;

import com.monesoft.refelin.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User registerUser(User user);
}
