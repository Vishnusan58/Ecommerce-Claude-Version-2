package com.ecommerce.ecommerce_backend.service.user;

import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.OrderRepository;
import com.ecommerce.ecommerce_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository,
                       OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public User getProfile(User user) {
        return user;
    }

    public User updateProfile(User user, String name, String phone) {
        user.setName(name);
        user.setPhone(phone);
        return userRepository.save(user);
    }

    public List<?> getOrderHistory(User user) {
        return orderRepository.findByUser(user);
    }

    public void requestSellerRole(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(UserRole.SELLER);
        user.setSellerVerified(false);

        userRepository.save(user);
    }

}
