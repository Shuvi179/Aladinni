package com.monesoft.refelin.controller;

import com.monesoft.refelin.dto.RegisterUserDTO;
import com.monesoft.refelin.dto.UserInfoDTO;
import com.monesoft.refelin.dto.UserLoginDTO;
import com.monesoft.refelin.manager.impl.UserManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserManager userManager;

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
