package com.monesoft.refelin.controller;

import com.monesoft.refelin.dto.CompanyInfoDTO;
import com.monesoft.refelin.manager.impl.CompanyManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyManager companyManager;

    @PutMapping
    @Operation(summary = "Update company info by id", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CompanyInfoDTO> updateCompany(@RequestBody CompanyInfoDTO company) {
        return ResponseEntity.ok(companyManager.updateCompany(company));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyInfoDTO> getCompany(@PathVariable Long id) {
        return ResponseEntity.ok(companyManager.getCompany(id));
    }
}
