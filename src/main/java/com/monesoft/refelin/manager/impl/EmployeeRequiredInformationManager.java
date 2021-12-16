package com.monesoft.refelin.manager.impl;

import com.monesoft.refelin.entity.EmployeeRequiredInformation;
import com.monesoft.refelin.entity.User;
import com.monesoft.refelin.mapper.EmployeeRequiredInformationMapper;
import com.monesoft.refelin.service.impl.EmployeeRequiredInformationService;
import com.monesoft.refelin.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeRequiredInformationManager {

    private final EmployeeRequiredInformationService employeeRequiredInformationService;
    private final EmployeeRequiredInformationMapper employeeRequiredInformationMapper;

    public void addDefaultEmployeeRequiredInformation(User user) {
        EmployeeRequiredInformation requiredInformation = employeeRequiredInformationMapper.map(user);
        employeeRequiredInformationService.addNewEmployeeRequiredInformation(requiredInformation);
    }

    public void confirmPricingRules() {
        User user = UserUtils.getCurrentUser();
        employeeRequiredInformationService.updatePricingConfirm(true, user);
    }
}
