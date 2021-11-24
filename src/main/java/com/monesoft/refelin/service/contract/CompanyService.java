package com.monesoft.refelin.service.contract;

import com.monesoft.refelin.entity.Company;

public interface CompanyService {
    Company addNewCompany(Company company);

    Company updateCompany(Company company);

    Company getCompany(Long id);
}
