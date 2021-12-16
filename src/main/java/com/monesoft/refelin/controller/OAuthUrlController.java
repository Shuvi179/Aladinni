package com.monesoft.refelin.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endpoint-url")
public class OAuthUrlController {

    @GetMapping("/employee")
    @Operation(summary = "Get Google redirect url for employee")
    public String getEmployeeGoogleUrl() {
        return "localhost:8080/api/v1/oauth2/authorization/google?redirect_uri=http://localhost:8080/api/v1/custom/employee&is_hr=true";
    }

    @GetMapping("/bounty-hunter")
    @Operation(summary = "Get Google redirect url for bounty hunter")
    public String getBountyHunterGoogleUrl() {
        return "localhost:8080/api/v1/oauth2/authorization/google?redirect_uri=http://localhost:8080/api/v1/custom/hunter";
    }
}
