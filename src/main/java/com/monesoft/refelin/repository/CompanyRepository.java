package com.monesoft.refelin.repository;

import com.monesoft.refelin.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByIdAndUserId(Long id, Long userId);
}
