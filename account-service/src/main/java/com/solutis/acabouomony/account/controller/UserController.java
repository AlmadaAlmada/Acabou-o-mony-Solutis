package com.solutis.acabouomony.account.controller;

import com.solutis.acabouomony.account.dto.request.UserReqDTO;
import com.solutis.acabouomony.account.dto.request.UserUpdDTO;
import com.solutis.acabouomony.account.dto.response.UserRespDTO;
import com.solutis.acabouomony.account.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user", description = "Creates a new user with provided details")
    @PostMapping("/create")
    public void createUser(
            @RequestBody @Valid UserReqDTO userReqDTO) {
        userService.createUser(userReqDTO);
    }

    @Operation(summary = "User login", description = "Authenticates user and returns OTP code")
    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password) {
        return userService.login(email, password);
    }

    @Operation(summary = "List all users", description = "Lists all users without pagination")
    @GetMapping("/list")
    public List<UserRespDTO> listUsers() {
        return userService.listUsers();
    }

    @Operation(summary = "Get user details", description = "Retrieves user details by ID")
    @GetMapping("/{userId}")
    public UserRespDTO detailUser(
            @PathVariable UUID userId) {
        return userService.detailUser(userId);
    }

    @Operation(summary = "Update user", description = "Updates user information")
    @PutMapping("/update")
    public void updateUser(
            @RequestBody @Valid UserUpdDTO userUpdateDTO) {
        userService.updateUser(userUpdateDTO);
    }

    @Operation(summary = "Delete user", description = "Deletes a user by ID")
    @DeleteMapping("/delete/{userId}")
    public void deleteUser(
            @PathVariable UUID userId) {
        userService.deleteUser(userId);
    }
}

