package com.monesoft.refelin.service.contract;

import com.monesoft.refelin.dto.UserLoginDTO;

public interface AuthenticationService {
    String authenticateUser(UserLoginDTO userLoginDTO);
}
