package com.solutis.acabouomony.account.controller;

import com.solutis.acabouomony.account.dto.request.UserReqDTO;
import com.solutis.acabouomony.account.dto.request.UserUpdDTO;
import com.solutis.acabouomony.account.dto.response.UserRespDTO;
import com.solutis.acabouomony.account.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {

    public final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with provided details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(defaultValue = "User created successfully"))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema)}, description = "Bad Request"),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema)}, description = "Internal Server Error")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserReqDTO userReqDTO) {
        userService.createUser(userReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User " + userReqDTO.getName() + " created!");
    }

    @Operation(
            summary = "User login",
            description = "Authenticates user and returns OTP code"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(defaultValue = "OTP code: 0000-0000-0000-0000"))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema)}, description = "Bad Request"),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema)}, description = "Internal Server Error")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String email,
            @RequestParam String password) {
        String otpCode = userService.login(email, password);
        return ResponseEntity.ok().body(otpCode);
    }

    @Operation(
            summary = "List all users",
            description = "Lists all users with pagination"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UserRespDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema)}, description = "Bad Request"),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema)}, description = "Internal Server Error")
    })
    @GetMapping("/list")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<UserRespDTO>> listUsers(Pageable pageable) {
        Page<UserRespDTO> users = userService.listUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Get user details",
            description = "Retrieves user details by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UserRespDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema)}, description = "Bad Request"),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema)}, description = "User Not Found"),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema)}, description = "Internal Server Error")
    })
    @GetMapping("/{userId}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserRespDTO> detailUser(@PathVariable UUID userId) {
        UserRespDTO userRespDTO = userService.detailUser(userId);
        return ResponseEntity.ok(userRespDTO);
    }

    @Operation(
            summary = "Update user information",
            description = "Updates user information (name, contact, or email)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(description = "User updated successfully"))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema)}, description = "Bad Request"),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema)}, description = "Internal Server Error")
    })
    @PutMapping("/update")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserUpdDTO userUpdDTO) {
        userService.updateUser(userUpdDTO);
        return ResponseEntity.ok().body("User updated successfully!");
    }

    @Operation(
            summary = "Delete user by ID",
            description = "Deletes a user by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema)}, description = "No Content"),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema)}, description = "Bad Request"),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema)}, description = "Internal Server Error")
    })
    @DeleteMapping("/delete/{userId}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
