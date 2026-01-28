package com.ecommerce.ecommerce_backend.service.user;

import com.ecommerce.ecommerce_backend.enums.PaymentStatus;
import com.ecommerce.ecommerce_backend.model.Order;
import com.ecommerce.ecommerce_backend.model.Payment;
import com.ecommerce.ecommerce_backend.repository.OrderRepository;
import com.ecommerce.ecommerce_backend.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Mock payment processing
     */
    public Payment makePayment(Long orderId, String paymentMethod) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(order.getTotalAmount());

        // Mock payment success
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId(UUID.randomUUID().toString());

        return paymentRepository.save(payment);
    }
}
