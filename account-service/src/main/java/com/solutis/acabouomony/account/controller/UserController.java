package com.solutis.acabouomony.account.controller;

import com.solutis.acabouomony.account.dto.UserDTO;
import com.solutis.acabouomony.account.model.User;
import com.solutis.acabouomony.account.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "User", description = "Endpoints relacionados ao gerenciamento de usuários")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Obter usuário por ID", description = "Recupera os dados de um usuário usando seu ID único.")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID userId) {
        Optional<User> userOptional = userService.getUserById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO(user.getUserId(), user.getName(), user.getCpf(), user.getContact(), user.getEmail());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obter usuário por e-mail", description = "Recupera os dados de um usuário usando seu e-mail.")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<User> userOptional = userService.getUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO(user.getUserId(), user.getName(), user.getCpf(), user.getContact(), user.getEmail());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atualizar dados do usuário", description = "Atualiza as informações de um usuário já existente.")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou erro na atualização")
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID userId, @RequestBody @Valid UserDTO userDTO) {
        Optional<User> existingUser = userService.getUserById(userId);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(userDTO.getName());
            user.setCpf(userDTO.getCpf());
            user.setContact(userDTO.getContact());
            user.setEmail(userDTO.getEmail());

            User updatedUser = userService.updateUser(user);
            UserDTO updatedUserDTO = new UserDTO(updatedUser.getUserId(), updatedUser.getName(), updatedUser.getCpf(), updatedUser.getContact(), updatedUser.getEmail());
            return ResponseEntity.ok(updatedUserDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
