package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.cart.*;
import com.ecommerce.ecommerce_backend.model.CartItem;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.user.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/cart")
public class CartController {

    private final CartService cartService;
    private final AuthService authService;

    public CartController(CartService cartService,
                          AuthService authService) {
        this.cartService = cartService;
        this.authService = authService;
    }

    @PostMapping("/add")
    public void addToCart(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody AddToCartDTO dto) {

        User user = authService.getUserById(userId);
        cartService.addItem(user, dto.getProductId(), dto.getQuantity());
    }

    @GetMapping
    public CartResponseDTO viewCart(
            @RequestHeader("X-USER-ID") Long userId) {

        User user = authService.getUserById(userId);

        List<CartItem> items = cartService.getCartItems(user);

        List<CartItemDTO> itemDTOs = items.stream().map(item -> {
            CartItemDTO dto = new CartItemDTO();
            dto.setCartItemId(item.getId());
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setPrice(item.getProduct().getPrice());
            dto.setQuantity(item.getQuantity());
            dto.setSubtotal(item.getQuantity() * item.getProduct().getPrice());
            return dto;
        }).collect(Collectors.toList());

        CartResponseDTO response = new CartResponseDTO();
        response.setItems(itemDTOs);
        response.setTotalAmount(
                itemDTOs.stream().mapToDouble(CartItemDTO::getSubtotal).sum()
        );
        response.setDiscountAmount(0);
        response.setFinalAmount(response.getTotalAmount());

        return response;
    }

    @DeleteMapping("/clear")
    public void clearCart(
            @RequestHeader("X-USER-ID") Long userId) {

        User user = authService.getUserById(userId);
        cartService.clearCart(user);
    }
}
