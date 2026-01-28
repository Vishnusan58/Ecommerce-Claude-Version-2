package com.ecommerce.ecommerce_backend.controller.admin;

import com.ecommerce.ecommerce_backend.dto.admin.AdminDeliveryZoneDTO;
import com.ecommerce.ecommerce_backend.model.DeliveryZone;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.admin.AdminDeliveryZoneService;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/delivery-zones")
public class AdminDeliveryZoneController {

    private final AdminDeliveryZoneService zoneService;
    private final AuthService authService;

    public AdminDeliveryZoneController(AdminDeliveryZoneService zoneService,
                                       AuthService authService) {
        this.zoneService = zoneService;
        this.authService = authService;
    }

    @PostMapping
    public DeliveryZone addZone(
            @RequestHeader("X-ADMIN-ID") Long adminId,
            @RequestBody DeliveryZone zone) {

        User admin = authService.getUserById(adminId);
        return zoneService.addZone(admin, zone);
    }

    @GetMapping
    public List<AdminDeliveryZoneDTO> getAllZones(
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        User admin = authService.getUserById(adminId);

        return zoneService.getAllZones(admin)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AdminDeliveryZoneDTO mapToDto(DeliveryZone zone) {
        AdminDeliveryZoneDTO dto = new AdminDeliveryZoneDTO();
        dto.setZoneId(zone.getId());
        dto.setPincode(zone.getPincode());
        dto.setActive(zone.isActive());
        return dto;
    }
}
