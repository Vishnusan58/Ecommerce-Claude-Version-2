package com.ecommerce.ecommerce_backend.service.admin;

import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.UserRepository;
import com.ecommerce.ecommerce_backend.util.AdminAuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {

    private final UserRepository userRepository;
    private final AdminAuthUtil adminAuthUtil;
    public AdminUserService(UserRepository userRepository, AdminAuthUtil adminAuthUtil) {
        this.userRepository = userRepository;
        this.adminAuthUtil = adminAuthUtil;
    }

    public List<User> getAllUsers(User admin) {
        adminAuthUtil.validateAdmin(admin);
        return userRepository.findAll();
    }

    public User getUserDetails(User admin, Long userId) {
        adminAuthUtil.validateAdmin(admin);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void changeUserRole(User admin, Long userId, UserRole role) {
        adminAuthUtil.validateAdmin(admin);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(role);
        userRepository.save(user);
    }

    public void disableUser(User admin, Long userId) {
        adminAuthUtil.validateAdmin(admin);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // soft-disable strategy (simple)
        user.setPassword("DISABLED");
        userRepository.save(user);
    }

    public List<User> getPendingSellers() {
        return userRepository
                .findByRoleAndSellerVerified(UserRole.SELLER, false);
    }

    public void approveSeller(Long sellerId) {

        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        if (seller.getRole() != UserRole.SELLER) {
            throw new RuntimeException("User is not a seller");
        }

        seller.setSellerVerified(true);
        userRepository.save(seller);
    }

    public void rejectSeller(Long sellerId) {

        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        seller.setRole(UserRole.CUSTOMER);
        seller.setSellerVerified(false);

        userRepository.save(seller);
    }



}
