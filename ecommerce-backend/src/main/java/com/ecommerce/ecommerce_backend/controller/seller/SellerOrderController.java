package com.ecommerce.ecommerce_backend.controller.seller;

import com.ecommerce.ecommerce_backend.dto.seller.SellerOrderDTO;
import com.ecommerce.ecommerce_backend.enums.OrderStatus;
import com.ecommerce.ecommerce_backend.model.OrderItem;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.seller.SellerOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/seller/orders")
public class SellerOrderController {

    private final SellerOrderService orderService;
    private final AuthService authService;

    public SellerOrderController(SellerOrderService orderService,
                                 AuthService authService) {
        this.orderService = orderService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<?> getSellerOrders(
            @RequestHeader(value = "X-SELLER-ID", required = false) Long sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status) {

        if (sellerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Seller authentication required"));
        }

        User seller = authService.getUserById(sellerId);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Seller not found"));
        }

        // Group order items by order ID to avoid duplicates
        Map<Long, SellerOrderDTO> orderMap = new LinkedHashMap<>();
        for (OrderItem item : orderService.getSellerOrders(seller)) {
            Long orderId = item.getOrder().getId();
            if (!orderMap.containsKey(orderId)) {
                orderMap.put(orderId, mapToDto(item));
            } else {
                // Increment item count for existing order
                SellerOrderDTO existing = orderMap.get(orderId);
                existing.setItemCount(existing.getItemCount() + 1);
            }
        }
        List<SellerOrderDTO> allOrders = new ArrayList<>(orderMap.values());

        // Filter by status if provided
        if (status != null && !status.trim().isEmpty()) {
            try {
                OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
                allOrders = allOrders.stream()
                        .filter(order -> order.getStatus().equals(orderStatus.name()))
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                // Invalid status, ignore filter
            }
        }

        // Manual pagination
        int totalElements = allOrders.size();
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        List<SellerOrderDTO> paginatedOrders = startIndex < totalElements
                ? allOrders.subList(startIndex, endIndex)
                : List.of();

        Map<String, Object> response = new HashMap<>();
        response.put("content", paginatedOrders);
        response.put("totalElements", totalElements);
        response.put("totalPages", (int) Math.ceil((double) totalElements / size));
        response.put("size", size);
        response.put("number", page);
        response.put("first", page == 0);
        response.put("last", endIndex >= totalElements);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(
            @RequestHeader(value = "X-SELLER-ID", required = false) Long sellerId,
            @PathVariable Long orderId,
            @RequestBody Map<String, String> request) {

        if (sellerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Seller authentication required"));
        }

        User seller = authService.getUserById(sellerId);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Seller not found"));
        }

        String status = request.get("status");
        if (status == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Status is required"));
        }

        try {
            OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
            orderService.updateOrderStatus(seller, orderId, newStatus);
            return ResponseEntity.ok(Map.of(
                    "message", "Order status updated successfully",
                    "status", newStatus.name()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid status: " + status));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    private SellerOrderDTO mapToDto(OrderItem item) {
        SellerOrderDTO dto = new SellerOrderDTO();
        dto.setId(item.getOrder().getId());
        dto.setOrderItemId(item.getId());
        dto.setOrderNumber("ORD-" + item.getOrder().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setTotal(item.getOrder().getFinalAmount());
        dto.setStatus(item.getOrder().getStatus().name());
        dto.setIsPremiumOrder(item.getOrder().isPriority());
        dto.setCreatedAt(item.getOrder().getOrderDate());
        return dto;
    }
}
