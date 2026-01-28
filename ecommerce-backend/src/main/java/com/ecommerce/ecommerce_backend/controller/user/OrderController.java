package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.order.*;
import com.ecommerce.ecommerce_backend.model.Order;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.model.UserAddress;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.user.AddressService;
import com.ecommerce.ecommerce_backend.service.user.OrderService;
import com.ecommerce.ecommerce_backend.dto.order.PlaceOrderDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/orders")
public class OrderController {

    private final OrderService orderService;
    private final AddressService addressService;
    private final AuthService authService;

    public OrderController(OrderService orderService,
                           AddressService addressService,
                           AuthService authService) {
        this.orderService = orderService;
        this.addressService = addressService;
        this.authService = authService;
    }

    @PostMapping
    public OrderResponseDTO placeOrder(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody PlaceOrderDTO dto) {

        User user = authService.getUserById(userId);

        UserAddress address = addressService.getAddresses(user)
                .stream()
                .filter(a -> a.getId().equals(dto.getAddressId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Order order = orderService.placeOrder(user, address,dto.getPreferredDeliveryDate());

        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(order.getId());
        response.setStatus(order.getStatus().name());
        response.setTotalAmount(order.getTotalAmount());

        return response;
    }

    @GetMapping
    public List<OrderSummaryDTO> getOrders(
            @RequestHeader("X-USER-ID") Long userId) {

        User user = authService.getUserById(userId);

        return orderService.getOrders(user)
                .stream()
                .map(order -> {
                    OrderSummaryDTO dto = new OrderSummaryDTO();
                    dto.setOrderId(order.getId());
                    dto.setOrderDate(order.getOrderDate());
                    dto.setStatus(order.getStatus().name());
                    dto.setFinalAmount(order.getTotalAmount());
                    return dto;
                }).collect(Collectors.toList());
    }
}
