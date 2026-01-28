package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.model.Order;
import com.ecommerce.ecommerce_backend.model.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {

    List<OrderStatusHistory> findByOrderOrderByChangedAtAsc(Order order);
}
