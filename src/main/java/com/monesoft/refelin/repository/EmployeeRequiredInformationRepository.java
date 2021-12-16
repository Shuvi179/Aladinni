package com.monesoft.refelin.repository;

import com.monesoft.refelin.entity.EmployeeRequiredInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRequiredInformationRepository extends JpaRepository<EmployeeRequiredInformation, Long> {
    @Modifying
    @Query("update EmployeeRequiredInformation info set info.pricingRuleConfirm = ?1 where info.user.id = ?2")
    void updatePricingRulesConfirmByUserId(Boolean confirm, Long userId);
}
