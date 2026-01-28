package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    List<UserAddress> findByUser(User user);

    UserAddress findByUserAndIsDefaultTrue(User user);
}
