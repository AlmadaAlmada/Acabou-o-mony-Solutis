package com.solutis.acabouomony.account.service;

import com.solutis.acabouomony.account.model.User;
import com.solutis.acabouomony.account.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class); // Inicializa o Logger

    private final UserRepository userRepository;
    @Getter
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String email, String password) {
        logger.info("Tentando fazer login para o usuário: {}", email); // Logando o email recebido

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.info("Usuário encontrado: {}", user.getEmail()); // Logando o usuário encontrado

            // Logando a senha que está sendo comparada (apenas para depuração)
            logger.debug("Senha fornecida: {}", password);
            logger.debug("Senha armazenada: {}", user.getPassword());  // A senha do banco (hash)

            if (passwordEncoder.matches(password, user.getPassword())) {
                logger.info("Login bem-sucedido para o usuário: {}", email);
                return generateToken(user);
            } else {
                logger.warn("Senha incorreta para o usuário: {}", email);
            }
        } else {
            logger.warn("Usuário não encontrado: {}", email);
        }

        throw new RuntimeException("Credenciais inválidas");
    }

    private String generateToken(User user) {
        logger.info("Gerando token para o usuário: {}", user.getEmail());
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getUserId().toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))  // 30 minutos
                .sign(Algorithm.HMAC256(secretKey));
    }

    public boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);
            logger.info("Token verificado com sucesso.");
            return true;
        } catch (Exception e) {
            logger.error("Falha na verificação do token: {}", e.getMessage());
            return false;
        }
    }

    public String validateToken(String token) {
        try {
            logger.info("Validando token.");
            return JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            logger.error("Falha na validação do token: {}", e.getMessage());
            return null;
        }
    }
}
