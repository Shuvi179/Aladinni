package com.monesoft.refelin.service.impl;

import com.monesoft.refelin.entity.Company;
import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.exception.PermissionException;
import com.monesoft.refelin.repository.CompanyRepository;
import com.monesoft.refelin.util.UserUtils;
import com.monesoft.refelin.util.ValidateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company addNewCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Company company) {
        User user = UserUtils.getCurrentUser();
        Company companyEntity = validateCompanyAccess(company, user);
        companyEntity.setDescription(company.getDescription());
        companyEntity.setName(company.getName());
        return companyRepository.save(companyEntity);
    }

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
