package com.solutis.acabouomony.account.controller;

import com.solutis.acabouomony.account.dto.request.LoginRequestDTO;
import com.solutis.acabouomony.account.dto.request.RegisterRequestDTO;
import com.solutis.acabouomony.account.service.AuthService;
import com.solutis.acabouomony.account.service.UserService;
import com.solutis.acabouomony.account.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "Auth", description = "Endpoints relacionados a autenticação e registro de usuários")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Operation(summary = "Testando o acesso ao authController antes de autenticar", description = "Verificando se o AUTH_WHITELIST esta sendo de fato liberado sem autenticação")
    @ApiResponse(responseCode = "200", description = "endpoint acessado com sucesso!!")
    @ApiResponse(responseCode = "401", description = "problemas ao acessar endpoint")
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("AuthController Test: Acesso permitido! 😎");
    }

    @Operation(summary = "Realizar login", description = "Autentica o usuário e retorna um token JWT.")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso!")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok("token: " + token);  // Retorna o token JWT
    }

    @Operation(summary = "Registrar novo usuário", description = "Registra um novo usuário no sistema e gera um token JWT.")
    @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso!")
    @ApiResponse(responseCode = "400", description = "Dados inválidos para o registro")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDTO registerRequest) {
        // Criptografa a senha antes de salvar
        String encryptedPassword = authService.getPasswordEncoder().encode(registerRequest.getPassword());

        // Cria o novo usuário com a senha criptografada
        User newUser = new User(
                null,  // UUID será gerado automaticamente
                registerRequest.getName(),
                registerRequest.getCpf(),
                registerRequest.getContact(),
                registerRequest.getEmail(),
                encryptedPassword,  // Senha criptografada
                registerRequest.getSecret(),
                registerRequest.getAnswer()
        );

        // Salva o novo usuário no banco de dados
        userService.createUser(newUser);

        // Gera o token JWT para o novo usuário
        String token = authService.login(registerRequest.getEmail(), registerRequest.getPassword());

        // Retorna o token JWT
        return ResponseEntity.status(201).body(token);
    }
}
