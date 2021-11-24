package com.monesoft.refelin.mapper;

import com.monesoft.refelin.dto.CompanyInfoDTO;
import com.monesoft.refelin.entity.Company;
import com.monesoft.refelin.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company map(CompanyInfoDTO companyInfoDTO, User user) {
        Company company = companyMap(companyInfoDTO);
        company.setUser(user);
        return company;
    }

    public Company map(CompanyInfoDTO companyInfoDTO) {
        return companyMap(companyInfoDTO);
    }

    private Company companyMap(CompanyInfoDTO companyInfoDTO) {
        Company company = new Company();
        company.setId(companyInfoDTO.getId());
        company.setName(companyInfoDTO.getName());
        company.setDescription(companyInfoDTO.getDescription());
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
