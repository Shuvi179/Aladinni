package com.monesoft.refelin.service.impl;

import com.monesoft.refelin.entity.Company;
import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.exception.PermissionException;
import com.monesoft.refelin.repository.CompanyRepository;
import com.monesoft.refelin.service.contract.CompanyService;
import com.monesoft.refelin.util.UserUtils;
import com.monesoft.refelin.util.ValidateUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company addNewCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Company company) {
        User user = UserUtils.getCurrentUser();
        Company companyEntity = validateCompanyAccess(company, user);
        companyEntity.setDescription(company.getDescription());
        companyEntity.setName(company.getName());
        return companyRepository.save(companyEntity);
    }

    @Override
    public Company getCompany(Long id) {
        return validateCompanyExist(companyRepository.findById(id));
    }

    private Company validateCompanyAccess(Company company, User user) {
        validateCompany(company);
        Optional<Company> companyInDB = companyRepository.findByIdAndUserId(company.getId(), user.getId());
        return validateCompanyExist(companyInDB);
    }

    private void validateCompany(Company company) {
        ValidateUtils.validateId(company.getId());
    }

    private Company validateCompanyExist(Optional<Company> company) {
        if (company.isEmpty()) {
            throw new PermissionException();
        }
        return company.get();
    }
}
