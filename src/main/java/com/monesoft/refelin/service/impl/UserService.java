package com.monesoft.refelin.service.impl;

import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.exception.DuplicateEmailException;
import com.monesoft.refelin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public User loadUserByUsername(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user with email: " + email);
        }
        return user.get();
    }

    @Transactional
    public User registerUser(User user) {
        validateRegisterUser(user);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    private void validateRegisterUser(User user) {
        validateEmail(user.getEmail());
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
