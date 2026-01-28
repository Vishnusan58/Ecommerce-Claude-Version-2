package com.ecommerce.ecommerce_backend.controller.admin;

import com.ecommerce.ecommerce_backend.dto.admin.AdminOrderResponseDTO;
import com.ecommerce.ecommerce_backend.enums.OrderStatus;
import com.ecommerce.ecommerce_backend.model.Order;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.admin.AdminOrderService;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final AdminOrderService orderService;
    private final AuthService authService;

    public AdminOrderController(AdminOrderService orderService,
                                AuthService authService) {
        this.orderService = orderService;
        this.authService = authService;
    }

    @GetMapping
    public List<AdminOrderResponseDTO> getAllOrders(
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        User admin = authService.getUserById(adminId);

        return orderService.getAllOrders(admin)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{orderId}/status")
    public void updateOrderStatus(
            @RequestHeader("X-ADMIN-ID") Long adminId,
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {

        User admin = authService.getUserById(adminId);
        orderService.forceUpdateOrderStatus(admin, orderId, status);
    }

    private AdminOrderResponseDTO mapToDto(Order order) {
        AdminOrderResponseDTO dto = new AdminOrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setUserEmail(order.getUser().getEmail());
        dto.setStatus(order.getStatus().name());
        dto.setFinalAmount(order.getTotalAmount());
        return dto;
    }
}
