package com.monesoft.refelin.mapper;

import com.monesoft.refelin.dto.RegisterUserDTO;
import com.monesoft.refelin.dto.UserInfoDTO;
import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.security.SecurityRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.monesoft.refelin.security.SecurityRole.ROLE_HR;

@Component
public class UserMapper {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserMapper(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User map(RegisterUserDTO registerDto) {
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setFullName(registerDto.getFullName());
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        user.setIsEnabled(false);
        if (registerDto.isHR()) {
            user.getRoles().add(SecurityRole.getRole(ROLE_HR));
        }
        return user;
    }

    public UserInfoDTO map(User user) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(user.getId());
        userInfoDTO.setEmail(user.getEmail());
        userInfoDTO.setFullName(user.getFullName());
        return userInfoDTO;
    }
}
