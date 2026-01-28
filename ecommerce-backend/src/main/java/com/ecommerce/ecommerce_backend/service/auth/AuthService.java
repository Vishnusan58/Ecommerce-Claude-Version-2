package com.ecommerce.ecommerce_backend.service.auth;

import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.UserRepository;
import com.ecommerce.ecommerce_backend.util.PasswordUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user
     */
    public User registerUser(String email,
                             String password,
                             String name,
                             String phone,
                             UserRole role) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(PasswordUtil.hashPassword(password));
        user.setName(name);
        user.setPhone(phone);
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    /**
     * Login user
     */
    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        boolean validPassword = PasswordUtil.verifyPassword(password, user.getPassword());
        if (!validPassword) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }

    /**
     * Utility method to fetch user by ID
     * (used by controllers when resolving X-USER-ID header)
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
