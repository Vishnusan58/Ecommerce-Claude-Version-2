package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.payment.PaymentRequestDTO;
import com.ecommerce.ecommerce_backend.dto.payment.PaymentResponseDTO;
import com.ecommerce.ecommerce_backend.model.Payment;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.user.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final AuthService authService;

    public PaymentController(PaymentService paymentService,
                             AuthService authService) {
        this.paymentService = paymentService;
        this.authService = authService;
    }

    @PostMapping
    public PaymentResponseDTO makePayment(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody PaymentRequestDTO dto) {

        authService.getUserById(userId);

        Payment payment = paymentService.makePayment(dto.getOrderId(), dto.getPaymentMethod());

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setPaymentId(payment.getId());
        response.setStatus(payment.getStatus().name());
        response.setTransactionId(payment.getTransactionId());
        return response;
    }
}
