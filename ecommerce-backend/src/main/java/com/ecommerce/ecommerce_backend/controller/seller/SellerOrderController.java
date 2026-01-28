package com.ecommerce.ecommerce_backend.controller.seller;

import com.ecommerce.ecommerce_backend.dto.seller.SellerOrderDTO;
import com.ecommerce.ecommerce_backend.model.OrderItem;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.seller.SellerOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<SellerOrderDTO> getSellerOrders(
            @RequestHeader("X-SELLER-ID") Long sellerId) {

        User seller = authService.getUserById(sellerId);

        return orderService.getSellerOrders(seller)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private SellerOrderDTO mapToDto(OrderItem item) {
        SellerOrderDTO dto = new SellerOrderDTO();
        dto.setOrderItemId(item.getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setOrderStatus(item.getOrder().getStatus().name());
        return dto;
    }
}
