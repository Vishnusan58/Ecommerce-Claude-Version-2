package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.order.*;
import com.ecommerce.ecommerce_backend.enums.OrderStatus;
import com.ecommerce.ecommerce_backend.model.Order;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.model.UserAddress;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.user.AddressService;
import com.ecommerce.ecommerce_backend.service.user.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> placeOrder(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody PlaceOrderDTO dto) {

        try {
            // Validate user
            User user = authService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found"));
            }

            UserAddress address = null;

            // Option 1: Use addressId if provided (saved address)
            if (dto.getAddressId() != null) {
                address = addressService.getAddresses(user)
                        .stream()
                        .filter(a -> a.getId().equals(dto.getAddressId()))
                        .findFirst()
                        .orElse(null);

                if (address == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("message", "Saved address not found"));
                }
            }
            // Option 2: Use inline shippingAddress from request (frontend's approach)
            else if (dto.getShippingAddress() != null) {
                PlaceOrderDTO.ShippingAddressDTO shippingAddr = dto.getShippingAddress();

                // Validate required address fields
                if (shippingAddr.getFullName() == null || shippingAddr.getFullName().trim().isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("message", "Full name is required"));
                }
                if (shippingAddr.getPhone() == null || shippingAddr.getPhone().trim().isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("message", "Phone number is required"));
                }
                if (shippingAddr.getStreet() == null || shippingAddr.getStreet().trim().isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("message", "Street address is required"));
                }
                if (shippingAddr.getCity() == null || shippingAddr.getCity().trim().isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("message", "City is required"));
                }
                if (shippingAddr.getState() == null || shippingAddr.getState().trim().isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("message", "State is required"));
                }
                if (shippingAddr.getZipCode() == null || shippingAddr.getZipCode().trim().isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("message", "Zip code is required"));
                }

                // Create a new UserAddress from the inline shipping address
                address = new UserAddress();
                address.setUser(user);
                address.setFullName(shippingAddr.getFullName());
                address.setPhone(shippingAddr.getPhone());
                address.setStreet(shippingAddr.getStreet());
                address.setCity(shippingAddr.getCity());
                address.setState(shippingAddr.getState());
                address.setZipCode(shippingAddr.getZipCode());
                address.setCountry(shippingAddr.getCountry() != null ? shippingAddr.getCountry() : "India");

                // Save the address
                address = addressService.saveAddress(address);
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Either addressId or shippingAddress is required"));
            }

            // Validate payment method
            String paymentMethod = dto.getPaymentMethod();
            if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
                paymentMethod = "COD"; // Default to Cash on Delivery
            }

            // Place the order
            Order order = orderService.placeOrder(
                    user,
                    address,
                    dto.getPreferredDeliveryDate(),
                    paymentMethod,
                    dto.getCouponCode()
            );

            // Build response
            OrderResponseDTO response = new OrderResponseDTO();
            response.setId(order.getId());
            response.setOrderNumber("ORD-" + order.getId());
            response.setStatus(order.getStatus().name());
            response.setPaymentMethod(paymentMethod);
            response.setPaymentStatus("PENDING");
            response.setSubtotal(order.getTotalAmount());
            response.setTax(0); // Calculate if needed
            response.setDeliveryFee(order.getDeliveryCharge());
            response.setDiscount(0); // Apply coupon discount if needed
            response.setTotal(order.getFinalAmount());
            response.setIsPremiumOrder(order.isPriority());
            response.setCreatedAt(order.getOrderDate());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            // Handle specific runtime exceptions with appropriate status codes
            String message = e.getMessage();
            if (message != null) {
                if (message.contains("Cart not found") || message.contains("Cart is empty")) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("message", message));
                }
                if (message.contains("not found")) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("message", message));
                }
                if (message.contains("premium")) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(Map.of("message", message));
                }
            }
            // Generic error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to place order: " + (message != null ? message : "Unknown error")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unexpected error occurred: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getOrders(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status) {

        try {
            User user = authService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found"));
            }

            List<Order> orders = orderService.getOrders(user);

            // Filter by status if provided
            if (status != null && !status.trim().isEmpty()) {
                try {
                    OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
                    orders = orders.stream()
                            .filter(order -> order.getStatus() == orderStatus)
                            .collect(Collectors.toList());
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("message", "Invalid order status: " + status + ". Valid values are: PLACED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED, RETURN_REQUESTED, RETURN_APPROVED, RETURN_REJECTED, REFUNDED"));
                }
            }

            // Apply pagination
            int totalElements = orders.size();
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, totalElements);
            List<Order> paginatedOrders = orders.subList(startIndex, endIndex);

            // Convert to DTOs with items
            List<OrderResponseDTO> orderDTOs = paginatedOrders.stream()
                    .map(order -> {
                        OrderResponseDTO dto = new OrderResponseDTO();
                        dto.setId(order.getId());
                        dto.setOrderNumber("ORD-" + order.getId());
                        dto.setStatus(order.getStatus().name());
                        dto.setPaymentMethod(order.getPaymentMethod());
                        dto.setPaymentStatus("COMPLETED"); // Assume completed for now
                        dto.setSubtotal(order.getTotalAmount());
                        dto.setTax(0); // Calculate if needed
                        dto.setDeliveryFee(order.getDeliveryCharge());
                        dto.setDiscount(0); // Apply coupon discount if needed
                        dto.setTotal(order.getFinalAmount());
                        dto.setIsPremiumOrder(order.isPriority());
                        dto.setCreatedAt(order.getOrderDate());

                        // Note: Items are not included in summary for performance
                        // Frontend should fetch individual orders for details
                        return dto;
                    }).collect(Collectors.toList());

            Map<String, Object> response = Map.of(
                    "content", orderDTOs,
                    "totalElements", totalElements,
                    "totalPages", (int) Math.ceil((double) totalElements / size),
                    "size", size,
                    "number", page,
                    "first", page == 0,
                    "last", endIndex >= totalElements
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to fetch orders: " + e.getMessage()));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long orderId) {
        try {
            User user = authService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found"));
            }

            Order order = orderService.getOrderById(orderId, user);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Order not found"));
            }

            OrderResponseDTO response = new OrderResponseDTO();
            response.setId(order.getId());
            response.setOrderNumber("ORD-" + order.getId());
            response.setStatus(order.getStatus().name());
            response.setSubtotal(order.getTotalAmount());
            response.setDeliveryFee(order.getDeliveryCharge());
            response.setTotal(order.getFinalAmount());
            response.setIsPremiumOrder(order.isPriority());
            response.setCreatedAt(order.getOrderDate());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to fetch order: " + e.getMessage()));
        }
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long orderId) {
        try {
            User user = authService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found"));
            }

            Order order = orderService.cancelOrder(orderId, user);

            OrderResponseDTO response = new OrderResponseDTO();
            response.setId(order.getId());
            response.setStatus(order.getStatus().name());
            response.setTotal(order.getFinalAmount());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to cancel order: " + e.getMessage()));
        }
    }

    @PostMapping("/{orderId}/refund")
    public ResponseEntity<?> requestRefund(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long orderId,
            @RequestBody Map<String, String> request) {
        try {
            User user = authService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found"));
            }

            String reason = request.get("reason");
            orderService.requestRefund(orderId, user, reason);

            return ResponseEntity.ok(Map.of("message", "Refund request submitted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to request refund: " + e.getMessage()));
        }
    }
}
