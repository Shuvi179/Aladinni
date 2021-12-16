package com.monesoft.refelin.service.impl;

import com.monesoft.refelin.entity.EmployeeRequiredInformation;
import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.repository.EmployeeRequiredInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeRequiredInformationService {

    private final EmployeeRequiredInformationRepository employeeRequiredInformationRepository;

    public EmployeeRequiredInformation addNewEmployeeRequiredInformation(EmployeeRequiredInformation requiredInformation) {
        return employeeRequiredInformationRepository.save(requiredInformation);
    }

    public void updatePricingConfirm(Boolean confirm, User user) {
        employeeRequiredInformationRepository.updatePricingRulesConfirmByUserId(confirm, user.getId());
    }
}
