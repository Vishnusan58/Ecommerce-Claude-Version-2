package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.order.OrderStatusTimelineDTO;
import com.ecommerce.ecommerce_backend.dto.order.OrderTrackingDTO;
import com.ecommerce.ecommerce_backend.model.Order;
import com.ecommerce.ecommerce_backend.repository.OrderRepository;
import com.ecommerce.ecommerce_backend.repository.OrderStatusHistoryRepository;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/orders/track")
public class OrderTrackingController {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;
    private final AuthService authService;

    public OrderTrackingController(OrderRepository orderRepository,
                                   OrderStatusHistoryRepository historyRepository,
                                   AuthService authService) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
        this.authService = authService;
    }

    @GetMapping("/{orderId}")
    public OrderTrackingDTO trackOrder(@RequestHeader("X-USER-ID") Long userId,
                                       @PathVariable Long orderId) {

        authService.getUserById(userId); // ownership validated via query

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderTrackingDTO dto = new OrderTrackingDTO();
        dto.setOrderId(order.getId());
        dto.setCurrentStatus(order.getStatus().name());

        dto.setTimeline(
                historyRepository.findByOrderOrderByChangedAtAsc(order)
                        .stream()
                        .map(h -> {
                            OrderStatusTimelineDTO t = new OrderStatusTimelineDTO();
                            t.setStatus(h.getStatus().name());
                            t.setTimestamp(h.getChangedAt());
                            return t;
                        }).collect(Collectors.toList())
        );
        return dto;
    }
}
