package com.solutis.acabouomony.util;

import java.security.SecureRandom;

public class SecurityUtil {
    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateOtpCode() {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int index = RANDOM.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }
    public static String generateVerificationCode() {
        return String.format("%06d", RANDOM.nextInt(1_000_000));
    }
}
