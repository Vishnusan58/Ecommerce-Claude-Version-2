package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.model.OrderItem;
import com.ecommerce.ecommerce_backend.model.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {

    Optional<ReturnRequest> findByOrderItem(OrderItem orderItem);
}
