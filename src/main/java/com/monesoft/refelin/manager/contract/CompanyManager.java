package com.monesoft.refelin.manager.contract;

import com.monesoft.refelin.dto.CompanyInfoDTO;
import com.monesoft.refelin.entity.User;

public interface CompanyManager {
    CompanyInfoDTO addNewCompany(User user, CompanyInfoDTO dto);

    CompanyInfoDTO updateCompany(CompanyInfoDTO dto);

    CompanyInfoDTO getCompany(Long id);
}
