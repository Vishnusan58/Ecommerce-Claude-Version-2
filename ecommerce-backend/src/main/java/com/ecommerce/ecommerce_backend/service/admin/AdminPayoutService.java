package com.ecommerce.ecommerce_backend.service.admin;

import com.ecommerce.ecommerce_backend.enums.PayoutStatus;
import com.ecommerce.ecommerce_backend.model.SellerPayout;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.SellerPayoutRepository;
import com.ecommerce.ecommerce_backend.util.AdminAuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPayoutService {
    private final AdminAuthUtil adminAuthUtil;
    private final SellerPayoutRepository payoutRepository;

    public AdminPayoutService(AdminAuthUtil adminAuthUtil, SellerPayoutRepository payoutRepository) {
        this.adminAuthUtil = adminAuthUtil;
        this.payoutRepository = payoutRepository;
    }

    public List<SellerPayout> getAllPayouts(User admin) {
        adminAuthUtil.validateAdmin(admin);
        return payoutRepository.findAll();
    }

    public void releasePayout(User admin, Long payoutId) {
        adminAuthUtil.validateAdmin(admin);

        SellerPayout payout = payoutRepository.findById(payoutId)
                .orElseThrow(() -> new RuntimeException("Payout not found"));

        payout.setStatus(PayoutStatus.RELEASED);
        payoutRepository.save(payout);
    }
}
