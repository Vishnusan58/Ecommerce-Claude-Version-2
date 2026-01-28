package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.returnrefund.ReturnRequestDTO;
import com.ecommerce.ecommerce_backend.dto.returnrefund.ReturnResponseDTO;
import com.ecommerce.ecommerce_backend.model.OrderItem;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.user.ReturnService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user/returns")
public class ReturnController {

    private final ReturnService returnService;
    private final AuthService authService;

    public ReturnController(ReturnService returnService,
                            AuthService authService) {
        this.returnService = returnService;
        this.authService = authService;
    }

    @PostMapping
    public ReturnResponseDTO requestReturn(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody ReturnRequestDTO dto) {

        User user = authService.getUserById(userId);

        OrderItem item = new OrderItem();
        item.setId(dto.getOrderItemId());

        var request = returnService.requestReturn(user, item, dto.getReason());

        ReturnResponseDTO response = new ReturnResponseDTO();
        response.setReturnId(request.getId());
        response.setStatus(request.getStatus().name());
        response.setRequestedAt(LocalDateTime.now());

        return response;
    }
}
