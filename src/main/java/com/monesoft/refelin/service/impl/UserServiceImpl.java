package com.monesoft.refelin.service.impl;

import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.exception.DuplicateEmailException;
import com.monesoft.refelin.repository.UserRepository;
import com.monesoft.refelin.service.contract.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findUserByEmail(email);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("No user with email: " + email);
        }
        return user;
    }

    @Transactional
    @Override
    public User registerUser(User user) {
        validateRegisterUser(user);
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
