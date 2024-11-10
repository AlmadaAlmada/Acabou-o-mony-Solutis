package com.solutis.acabouomony.security.repository;

import com.solutis.acabouomony.security.model.MultiFactorAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MultiFactorAuthRepository extends JpaRepository <MultiFactorAuth, UUID>{
    Optional<MultiFactorAuth> findByCode(String code);
}
