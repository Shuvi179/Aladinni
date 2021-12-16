package com.monesoft.refelin.manager.impl;

import com.monesoft.refelin.dto.CompanyInfoDTO;
import com.monesoft.refelin.entity.Company;
import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.mapper.CompanyMapper;
import com.monesoft.refelin.service.impl.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyManager {

    private final CompanyMapper companyMapper;
    private final CompanyService companyService;

    public void addDefaultCompany(User user) {
        Company company = companyMapper.map(user);
        companyService.addNewCompany(company);
    }

    public CompanyInfoDTO updateCompany(CompanyInfoDTO dto) {
        Company company = companyMapper.map(dto);
        return companyMapper.map(companyService.updateCompany(company));
    }

    public CompanyInfoDTO getCompany(Long id) {
        Company company = companyService.getCompany(id);
        return companyMapper.map(company);
    }
}
