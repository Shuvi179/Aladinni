package com.monesoft.refelin.controller;

import com.monesoft.refelin.manager.impl.EmployeeRequiredInformationManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/required-information")
@RequiredArgsConstructor
public class EmployeeRequiredInformationController {

    private final EmployeeRequiredInformationManager employeeRequiredInformationManager;

    @PutMapping("/pricing")
    @Operation(summary = "Confirm Pricing Rules for current user", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<HttpStatus> confirmPricingRules() {
        employeeRequiredInformationManager.confirmPricingRules();
        return ResponseEntity.ok().build();
    }
}
