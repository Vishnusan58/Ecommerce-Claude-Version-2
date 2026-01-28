package com.ecommerce.ecommerce_backend.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    private PasswordUtil() {
        // utility class
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static boolean verifyPassword(String rawPassword, String storedHash) {
        String hashedInput = hashPassword(rawPassword);
        return hashedInput.equals(storedHash);
    }
}

