package com.monesoft.refelin.manager.impl;

import com.monesoft.refelin.dto.RegisterUserDTO;
import com.monesoft.refelin.dto.UserInfoDTO;
import com.monesoft.refelin.dto.UserLoginDTO;
import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.mapper.UserMapper;
import com.monesoft.refelin.service.impl.AuthenticationService;
import com.monesoft.refelin.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserManager {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;
    private final CompanyManager companyManager;
    private final EmployeeRequiredInformationManager employeeRequiredInformationManager;

    public String authenticateUser(UserLoginDTO userLoginDTO) {
        return authenticationService.authenticateUser(userLoginDTO);
    }

    @Transactional
    public UserInfoDTO registerUser(RegisterUserDTO registerUserDTO) {
        User newUser = userMapper.map(registerUserDTO);
        User userAfterRegister = userService.registerUser(newUser);
        if (registerUserDTO.isHR()) {
            companyManager.addDefaultCompany(userAfterRegister);
            employeeRequiredInformationManager.addDefaultEmployeeRequiredInformation(userAfterRegister);
        }
        return userMapper.map(userAfterRegister);
    }
}
