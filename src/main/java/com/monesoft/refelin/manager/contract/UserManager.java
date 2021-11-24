package com.monesoft.refelin.manager.contract;

import com.monesoft.refelin.dto.RegisterUserDTO;
import com.monesoft.refelin.dto.UserInfoDTO;
import com.monesoft.refelin.dto.UserLoginDTO;

public interface UserManager {
    String authenticateUser(UserLoginDTO userLoginDTO);

    UserInfoDTO registerUser(RegisterUserDTO registerUserDTO);
}
