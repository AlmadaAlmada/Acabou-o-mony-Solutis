package com.solutis.acabouomony.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Token {
    private String accessToken;

    @Override
    public String toString() {
        return accessToken;
    }
}