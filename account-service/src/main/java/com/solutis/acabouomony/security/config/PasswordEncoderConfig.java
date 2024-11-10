package com.solutis.acabouomony.security.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public @NotBlank String encode(@NotBlank String password) {
        return password;
    }

    public boolean matches(String password, String password1) {
        return false;
    }
}
