package com.monesoft.refelin.dto;

import com.monesoft.refelin.validation.annotation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RegisterUserDTO {
    @ValidEmail
    @NotEmpty
    @NotNull
    private String email;
    @NotEmpty
    @NotNull
    private String password;
    @NotEmpty
    @NotNull
    private String fullName;
    private boolean isHR;
    private CompanyInfoDTO companyInfo;
}
