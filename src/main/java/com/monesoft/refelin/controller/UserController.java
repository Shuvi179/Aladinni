package com.monesoft.refelin.controller;

import com.monesoft.refelin.dto.RegisterUserDTO;
import com.monesoft.refelin.dto.UserInfoDTO;
import com.monesoft.refelin.dto.UserLoginDTO;
import com.monesoft.refelin.manager.contract.UserManager;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserManager userManager;

    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PostMapping("/login")
    @Operation(summary = "Login in application. Return a JWT token")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(userManager.authenticateUser(userLoginDTO));
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new User")
    public ResponseEntity<UserInfoDTO> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        return ResponseEntity.ok(userManager.registerUser(registerUserDTO));
    }
}
