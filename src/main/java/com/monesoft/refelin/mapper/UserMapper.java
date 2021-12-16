package com.monesoft.refelin.mapper;

import com.monesoft.refelin.dto.RegisterUserDTO;
import com.monesoft.refelin.dto.UserInfoDTO;
import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.entity.oauth.LoginClientId;
import com.monesoft.refelin.entity.oauth.OAuth2UserInfo;
import com.monesoft.refelin.security.SecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.monesoft.refelin.security.SecurityRole.ROLE_HR;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User map(RegisterUserDTO registerDto) {
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setFullName(registerDto.getFullName());
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        user.setIsEnabled(false);
        user.setProvider(LoginClientId.SIMPLE.getClientId());
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

    public User map(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo, boolean isHr) {
        User user = new User();
        user.setProvider(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        user.setFullName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
        user.setIsEnabled(true);
        if (isHr) {
            user.getRoles().add(SecurityRole.getRole(ROLE_HR));
        }
        return user;
    }
}
