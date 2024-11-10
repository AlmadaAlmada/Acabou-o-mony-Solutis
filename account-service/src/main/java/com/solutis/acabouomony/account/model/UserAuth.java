package com.solutis.acabouomony.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;

    private String email;

    @JsonIgnore
    private String password;

    private String otpCode;

    private boolean isUsed;

    public UserAuth(UUID userId, String otpCode, Boolean isUsed) {
        this.userId = userId;
        this.otpCode = otpCode;
        this.isUsed = isUsed;
    }
}