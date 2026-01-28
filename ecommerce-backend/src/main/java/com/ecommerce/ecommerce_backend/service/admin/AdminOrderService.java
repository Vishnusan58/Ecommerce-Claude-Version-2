package com.ecommerce.ecommerce_backend.service.admin;

import com.ecommerce.ecommerce_backend.enums.OrderStatus;
import com.ecommerce.ecommerce_backend.model.Order;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.OrderRepository;
import com.ecommerce.ecommerce_backend.util.AdminAuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;
    private final AdminAuthUtil adminAuthUtil;
    public AdminOrderService(OrderRepository orderRepository, AdminAuthUtil adminAuthUtil) {
        this.orderRepository = orderRepository;
        this.adminAuthUtil = adminAuthUtil;
    }

    public List<Order> getAllOrders() {
        return orderRepository
                .findAllByOrderByPriorityDescOrderDateAsc();
    }

    public List<Order> getAllOrders(User admin) {
        // admin already validated in controller
        return orderRepository
                .findAllByOrderByPriorityDescOrderDateAsc();
    }

    public Order getOrderDetails(User admin, Long orderId) {
        adminAuthUtil.validateAdmin(admin);
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void forceUpdateOrderStatus(User admin, Long orderId, OrderStatus status) {
        adminAuthUtil.validateAdmin(admin);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        orderRepository.save(order);
    }
}
