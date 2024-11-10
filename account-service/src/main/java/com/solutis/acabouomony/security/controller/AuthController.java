package com.solutis.acabouomony.security.controller;

import com.solutis.acabouomony.security.service.MultiFactorAuthService;
import com.solutis.acabouomony.security.dto.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final MultiFactorAuthService mFAService;

    public AuthController(MultiFactorAuthService mFAService) {
        this.mFAService = mFAService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateConfirmationCode(@RequestHeader String email, @RequestHeader String otpCode){
        mFAService.generateVerificationCode(email, otpCode);
        return ResponseEntity.status(201).body("Código enviado!");
    }

    @PostMapping("/confirmation")
    public ResponseEntity<Token> verifyCode(@RequestHeader String code, @RequestHeader String otpCode){
        Token token = mFAService.verifyVerificationCode(code, otpCode);
        return ResponseEntity.ok(token);
    }
}

