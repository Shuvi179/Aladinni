package com.monesoft.refelin.controller;

import com.monesoft.refelin.dto.CompanyInfoDTO;
import com.monesoft.refelin.manager.contract.CompanyManager;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyManager companyManager;

    public CompanyController(CompanyManager companyManager) {
        this.companyManager = companyManager;
    }

    @PutMapping
    @Operation(summary = "Update company info by id")
    public ResponseEntity<CompanyInfoDTO> updateCompany(@RequestBody CompanyInfoDTO company) {
        return ResponseEntity.ok(companyManager.updateCompany(company));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyInfoDTO> getCompany(@PathVariable Long id) {
        return ResponseEntity.ok(companyManager.getCompany(id));
    }
}
