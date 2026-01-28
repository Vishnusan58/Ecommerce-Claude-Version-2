package com.ecommerce.ecommerce_backend.service.seller;

import com.ecommerce.ecommerce_backend.enums.OrderStatus;
import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.OrderItem;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerOrderService {

    private final OrderItemRepository orderItemRepository;

    public SellerOrderService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getSellerOrders(User seller) {
        return orderItemRepository
                .findBySellerOrderByOrder_PriorityDescOrder_OrderDateAsc(seller);
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
