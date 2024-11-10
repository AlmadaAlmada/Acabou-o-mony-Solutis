package com.solutis.acabouomony.account.repository;

import com.solutis.acabouomony.account.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAuthRepository extends JpaRepository <UserAuth, Long>{
    UserAuth findUserAuthByUserIdAndIsUsedIsFalse(UUID userId);

    UserAuth findUserAuthByOtpCodeAndIsUsedIsFalse(String otpCode);
}
