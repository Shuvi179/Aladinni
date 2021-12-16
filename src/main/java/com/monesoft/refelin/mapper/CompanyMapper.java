package com.monesoft.refelin.mapper;

import com.monesoft.refelin.dto.CompanyInfoDTO;
import com.monesoft.refelin.entity.Company;
import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.util.UserUtils;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company map(CompanyInfoDTO companyInfoDTO) {
        Company company = new Company();
        company.setId(companyInfoDTO.getId());
        company.setName(companyInfoDTO.getName());
        company.setDescription(companyInfoDTO.getDescription());
        company.setUser(UserUtils.getCurrentUser());
        return company;
    }

    public Company map(User user) {
        Company company = new Company();
        company.setUser(user);
        return company;
    }

    public CompanyInfoDTO map(Company company) {
        CompanyInfoDTO companyDto = new CompanyInfoDTO();
        companyDto.setId(company.getId());
        companyDto.setName(company.getName());
        companyDto.setDescription(company.getDescription());
        return companyDto;
    }
}
