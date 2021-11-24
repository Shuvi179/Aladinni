package com.monesoft.refelin.manager.impl;

import com.monesoft.refelin.dto.RegisterUserDTO;
import com.monesoft.refelin.dto.UserInfoDTO;
import com.monesoft.refelin.dto.UserLoginDTO;
import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.manager.contract.CompanyManager;
import com.monesoft.refelin.manager.contract.UserManager;
import com.monesoft.refelin.mapper.UserMapper;
import com.monesoft.refelin.service.contract.AuthenticationService;
import com.monesoft.refelin.service.contract.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UserManagerImpl implements UserManager {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;
    private final CompanyManager companyManager;


    public UserManagerImpl(UserService userService, AuthenticationService authenticationService, UserMapper userMapper,
                           CompanyManager companyManager) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
        this.companyManager = companyManager;
    }

    @Override
    public String authenticateUser(UserLoginDTO userLoginDTO) {
        return authenticationService.authenticateUser(userLoginDTO);
    }

    @Override
    @Transactional
    public UserInfoDTO registerUser(RegisterUserDTO registerUserDTO) {
        User newUser = userMapper.map(registerUserDTO);
        User userAfterRegister = userService.registerUser(newUser);
        if (registerUserDTO.isHR()) {
            companyManager.addNewCompany(userAfterRegister, registerUserDTO.getCompanyInfo());
        }
        return userMapper.map(userAfterRegister);
    }
}
