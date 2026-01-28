package com.ecommerce.ecommerce_backend.util;

import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component   // ✅ THIS WAS MISSING
public class AdminAuthUtil {

    private final UserRepository userRepository;

    public AdminAuthUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // existing method (do not remove)
    public void validateAdmin(User user) {
        if (user == null || user.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Access denied: Admin only");
        }
    }

    // overloaded method (used by controllers)
    public User validateAdmin(Long adminId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        validateAdmin(admin);
        return admin;
    }
}
