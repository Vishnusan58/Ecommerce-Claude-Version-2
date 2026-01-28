package com.ecommerce.ecommerce_backend.util;


import com.ecommerce.ecommerce_backend.model.DeliveryZone;

import java.util.List;

public class DeliveryZoneValidator {

    private DeliveryZoneValidator() {
        // utility class
    }

    public static boolean isPincodeServiceable(
            String pincode,
            List<DeliveryZone> activeZones
    ) {
        if (pincode == null || activeZones == null) {
            return false;
        }

        for (DeliveryZone zone : activeZones) {
            if (zone.isActive() && pincode.equals(zone.getPincode())) {
                return true;
            }
        }
        return false;
    }
}

