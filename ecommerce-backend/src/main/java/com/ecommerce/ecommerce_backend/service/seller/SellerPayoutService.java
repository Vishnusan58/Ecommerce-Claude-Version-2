package com.ecommerce.ecommerce_backend.service.seller;

import com.ecommerce.ecommerce_backend.enums.PayoutStatus;
import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.SellerPayout;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.SellerPayoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerPayoutService {

    private final SellerPayoutRepository payoutRepository;

    public SellerPayoutService(SellerPayoutRepository payoutRepository) {
        this.payoutRepository = payoutRepository;
    }

    public List<SellerPayout> getSellerPayouts(User seller) {
        validateSeller(seller);
        return payoutRepository.findBySeller(seller);
    }

    public SellerPayout requestPayout(User seller, double amount) {
        validateSeller(seller);

        SellerPayout payout = new SellerPayout();
        payout.setSeller(seller);
        payout.setAmount(amount);
        payout.setStatus(PayoutStatus.PENDING);

        return payoutRepository.save(payout);
    }

    private void validateSeller(User seller) {
        if (seller == null || seller.getRole() != UserRole.SELLER) {
            throw new RuntimeException("Seller access only");
        }
    }
}
