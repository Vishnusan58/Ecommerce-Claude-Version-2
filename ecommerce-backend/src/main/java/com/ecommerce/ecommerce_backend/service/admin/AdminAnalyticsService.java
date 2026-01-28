package com.ecommerce.ecommerce_backend.service.admin;

import com.ecommerce.ecommerce_backend.model.Order;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.OrderRepository;
import com.ecommerce.ecommerce_backend.repository.UserRepository;
import com.ecommerce.ecommerce_backend.util.AdminAuthUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminAnalyticsService {
    private final AdminAuthUtil adminAuthUtil;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public AdminAnalyticsService(AdminAuthUtil adminAuthUtil, UserRepository userRepository,
                                 OrderRepository orderRepository) {
        this.adminAuthUtil = adminAuthUtil;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public Map<String, Object> getDashboardOverview(User admin) {
        adminAuthUtil.validateAdmin(admin);

        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("totalUsers", userRepository.count());
        dashboard.put("totalOrders", orderRepository.count());

        return dashboard;
    }

    public double getTotalRevenue(User admin) {
        adminAuthUtil.validateAdmin(admin);

        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();
    }
}
