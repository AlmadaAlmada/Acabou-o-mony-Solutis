package com.solutis.acabouomony.security.service;

import com.solutis.acabouomony.account.model.UserAuth;
import com.solutis.acabouomony.account.model.User;
import com.solutis.acabouomony.account.repository.UserAuthRepository;
import com.solutis.acabouomony.account.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthRepository authRepository;
    private static final String SECRET_KEY = "acabouomony-solutis";
    private static final long EXPIRATION_TIME = 600_000;

    public String attemptAuthentication(String email, String senha) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, user.getPassword())) {
            return "Não autorizado";
        }
        return generateToken(user.getEmail());
    }

    public String doAuthentication(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        UserAuth userAuth = authRepository.findUserAuthByUserIdAndIsUsedIsFalse(userId);
        userAuth.setUsed(true);
        authRepository.save(userAuth);

        return generateToken(user.getEmail());
    }

    private String generateToken(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY.getBytes()));
    }
}
