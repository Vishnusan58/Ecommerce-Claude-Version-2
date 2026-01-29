package com.ecommerce.ecommerce_backend.service.seller;

import com.ecommerce.ecommerce_backend.enums.OrderStatus;
import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.Order;
import com.ecommerce.ecommerce_backend.model.OrderItem;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.OrderItemRepository;
import com.ecommerce.ecommerce_backend.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SellerOrderService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public SellerOrderService(OrderItemRepository orderItemRepository,
                              OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderItem> getSellerOrders(User seller) {
        return orderItemRepository
                .findBySellerOrderByOrder_PriorityDescOrder_OrderDateAsc(seller);
    }

    @Transactional
    public Order updateOrderStatus(User seller, Long orderId, OrderStatus newStatus) {
        validateSeller(seller);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Validate that the seller owns at least one item in this order
        List<OrderItem> sellerItems = orderItemRepository.findBySellerOrderByOrder_PriorityDescOrder_OrderDateAsc(seller);
        boolean sellerOwnsItem = sellerItems.stream()
                .anyMatch(item -> item.getOrder().getId().equals(orderId));

        if (!sellerOwnsItem) {
            throw new RuntimeException("Unauthorized: You don't have items in this order");
        }

        // Validate status transition
        OrderStatus currentStatus = order.getStatus();
        validateStatusTransition(currentStatus, newStatus);

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    private void validateStatusTransition(OrderStatus current, OrderStatus newStatus) {
        // Sellers can only:
        // PLACED -> CONFIRMED (confirm order)
        // CONFIRMED -> SHIPPED (mark as shipped)

        if (current == OrderStatus.PLACED && newStatus == OrderStatus.CONFIRMED) {
            return; // Valid: Confirming a new order
        }

        if (current == OrderStatus.CONFIRMED && newStatus == OrderStatus.SHIPPED) {
            return; // Valid: Shipping a confirmed order
        }

        throw new RuntimeException(
                String.format("Invalid status transition: Cannot change from %s to %s. " +
                        "Sellers can only confirm PLACED orders or ship CONFIRMED orders.",
                        current, newStatus)
        );
    }

    public void updateOrderItemStatus(User seller,
                                      OrderItem orderItem,
                                      OrderStatus status) {
        validateSeller(seller);

        if (!orderItem.getSeller().getId().equals(seller.getId())) {
            throw new RuntimeException("Unauthorized order update");
        }

        orderItem.getOrder().setStatus(status);
    }

    private void validateSeller(User seller) {
        if (seller == null || seller.getRole() != UserRole.SELLER) {
            throw new RuntimeException("Seller access only");
        }
        if (!seller.isSellerVerified()) {
            throw new RuntimeException(
                    "Seller is not verified to process orders"
            );
        }
    }
}
