package com.ecommerce.ecommerce_backend.service.admin;

import com.ecommerce.ecommerce_backend.model.DeliveryZone;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.DeliveryZoneRepository;
import com.ecommerce.ecommerce_backend.util.AdminAuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDeliveryZoneService {

    private final DeliveryZoneRepository deliveryZoneRepository;
    private final AdminAuthUtil adminAuthUtil;
    public AdminDeliveryZoneService(DeliveryZoneRepository deliveryZoneRepository, AdminAuthUtil adminAuthUtil) {
        this.deliveryZoneRepository = deliveryZoneRepository;
        this.adminAuthUtil = adminAuthUtil;
    }

    public DeliveryZone addZone(User admin, DeliveryZone zone) {
        adminAuthUtil.validateAdmin(admin);
        return deliveryZoneRepository.save(zone);
    }

    public List<DeliveryZone> getAllZones(User admin) {
        adminAuthUtil.validateAdmin(admin);
        return deliveryZoneRepository.findAll();
    }

    public void disableZone(User admin, Long zoneId) {
        adminAuthUtil.validateAdmin(admin);

        DeliveryZone zone = deliveryZoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        zone.setActive(false);
        deliveryZoneRepository.save(zone);
    }
}
