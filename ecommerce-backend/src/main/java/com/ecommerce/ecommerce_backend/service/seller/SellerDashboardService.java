package com.ecommerce.ecommerce_backend.service.seller;

import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.OrderItem;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SellerDashboardService {

    private final OrderItemRepository orderItemRepository;

    public SellerDashboardService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public Map<String, Object> getSellerSummary(User seller) {
        validateSeller(seller);

        List<OrderItem> items = orderItemRepository.findBySeller(seller);

        double totalRevenue = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalOrders", items.size());
        summary.put("totalRevenue", totalRevenue);

        return summary;
    }

    private void validateSeller(User seller) {
        if (seller == null || seller.getRole() != UserRole.SELLER) {
            throw new RuntimeException("Seller access only");
        }
    }
}
