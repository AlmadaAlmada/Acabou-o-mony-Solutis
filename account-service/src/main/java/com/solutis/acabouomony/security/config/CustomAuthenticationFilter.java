package com.solutis.acabouomony.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private static final String SECRET_KEY = "acabouomony-solutis";
    private static final long EXPIRATION_TIME = 600_000;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getParameter("username"), request.getParameter("password"))
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        String token = generateToken(((User) authResult.getPrincipal()).getUsername(), request);
        writeResponse(response, Map.of("token", token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        writeResponse(response, Map.of("error", "Autenticação falhou, verifique suas credenciais"));
    }

    private String generateToken(String username, HttpServletRequest request) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withIssuer(request.getRequestURI())
                .sign(Algorithm.HMAC256(SECRET_KEY.getBytes()));
    }

    private void writeResponse(HttpServletResponse response, Map<String, String> data) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), data);
    }
}