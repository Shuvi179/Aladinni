package com.monesoft.refelin.manager.impl;

import com.monesoft.refelin.dto.CompanyInfoDTO;
import com.monesoft.refelin.entity.Company;
import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.manager.contract.CompanyManager;
import com.monesoft.refelin.mapper.CompanyMapper;
import com.monesoft.refelin.service.contract.CompanyService;
import org.springframework.stereotype.Service;

@Service
public class CompanyManagerImpl implements CompanyManager {

    private final CompanyMapper companyMapper;
    private final CompanyService companyService;

    public CompanyManagerImpl(CompanyMapper companyMapper, CompanyService companyService) {
        this.companyMapper = companyMapper;
        this.companyService = companyService;
    }

    @Override
    public CompanyInfoDTO addNewCompany(User user, CompanyInfoDTO dto) {
        Company company = companyMapper.map(dto, user);
        return companyMapper.map(companyService.addNewCompany(company));
    }

    @Override
    public CompanyInfoDTO updateCompany(CompanyInfoDTO dto) {
        Company company = companyMapper.map(dto);
        return companyMapper.map(companyService.updateCompany(company));
    }

    @Override
    public CompanyInfoDTO getCompany(Long id) {
        Company company = companyService.getCompany(id);
        return companyMapper.map(company);
    }
}
