package com.monesoft.refelin.mapper;

import com.monesoft.refelin.entity.EmployeeRequiredInformation;
import com.monesoft.refelin.entity.User;
import org.springframework.stereotype.Component;

@Component
public class EmployeeRequiredInformationMapper {
    public EmployeeRequiredInformation map (User user) {
        EmployeeRequiredInformation requiredInformation = new EmployeeRequiredInformation();
        requiredInformation.setUser(user);
        requiredInformation.setPricingRuleConfirm(false);
        return requiredInformation;
    }
}
