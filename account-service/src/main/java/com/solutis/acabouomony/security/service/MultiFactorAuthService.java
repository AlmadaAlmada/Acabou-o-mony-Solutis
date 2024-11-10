package com.solutis.acabouomony.security.service;

import com.solutis.acabouomony.account.model.UserAuth;
import com.solutis.acabouomony.account.service.UserService;
import com.solutis.acabouomony.security.rabbit.producer.SecurityProducer;
import com.solutis.acabouomony.security.dto.MultiFactorAuthDTO;
import com.solutis.acabouomony.security.dto.Token;
import com.solutis.acabouomony.security.model.MultiFactorAuth;
import com.solutis.acabouomony.security.repository.MultiFactorAuthRepository;
import com.solutis.acabouomony.util.SecurityUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;
import org.springframework.stereotype.Service;

@Service
public class MultiFactorAuthService {

    private final MultiFactorAuthRepository mFARepository;
    private final SecurityProducer securityProducer;
    private final UserService userService;
    private final AuthService authService;

    public MultiFactorAuthService(MultiFactorAuthRepository mFARepository, SecurityProducer securityProducer, UserService userService, AuthService authService) {
        this.mFARepository = mFARepository;
        this.securityProducer = securityProducer;
        this.userService = userService;
        this.authService = authService;
    }

    @Transactional
    public String generateVerificationCode(String email, String otpCode){
        if (!userService.validateOTP(otpCode)) {
            throw new NotAuthorizedException("otpCode inválido");
        }

        String code = SecurityUtil.generateVerificationCode();
        UserAuth userAuth = userService.getOtpEntity(otpCode);
        MultiFactorAuth multiFactorAuth = new MultiFactorAuth(userAuth.getUserId(), code, email);
        mFARepository.save(multiFactorAuth);
        securityProducer.publishMessageEmail(multiFactorAuth);

        return "Código enviado!";
    }

    public Token verifyVerificationCode(String code, String otpCode){
        if (!userService.validateOTP(otpCode)) {
            throw new NotAuthorizedException("otpCode inválido");
        }

        UserAuth userAuth = userService.getOtpEntity(otpCode);
        MultiFactorAuthDTO multiFactorAuthDTO = new MultiFactorAuthDTO(code, userAuth.getUserId());

        MultiFactorAuth multiFactorAuth = mFARepository.findByCode(multiFactorAuthDTO.verificationCode())
                .orElseThrow(() -> new NotAuthorizedException("Código de verificação inválido"));

        if (!multiFactorAuthDTO.userId().equals(multiFactorAuth.getUserId()) || multiFactorAuth.getIsUsed()) {
            throw new NotAuthorizedException("Código já utilizado ou inválido");
        }

        multiFactorAuth.setIsUsed(true);
        mFARepository.delete(multiFactorAuth);
        return new Token(authService.doAuthentication(userAuth.getUserId()));
    }
}
