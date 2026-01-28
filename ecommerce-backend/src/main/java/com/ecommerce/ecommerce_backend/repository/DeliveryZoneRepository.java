package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.model.DeliveryZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryZoneRepository extends JpaRepository<DeliveryZone, Long> {

    List<DeliveryZone> findByActiveTrue();
}
