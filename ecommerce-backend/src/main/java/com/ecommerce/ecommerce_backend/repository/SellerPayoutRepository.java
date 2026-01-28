package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.model.SellerPayout;
import com.ecommerce.ecommerce_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerPayoutRepository extends JpaRepository<SellerPayout, Long> {

    List<SellerPayout> findBySeller(User seller);
}
