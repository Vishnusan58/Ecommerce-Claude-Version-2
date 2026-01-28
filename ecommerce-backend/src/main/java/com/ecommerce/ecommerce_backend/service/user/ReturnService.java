package com.ecommerce.ecommerce_backend.service.user;

import com.ecommerce.ecommerce_backend.enums.ReturnStatus;
import com.ecommerce.ecommerce_backend.model.*;
import com.ecommerce.ecommerce_backend.repository.ReturnRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class ReturnService {

    private final ReturnRequestRepository returnRepository;

    public ReturnService(ReturnRequestRepository returnRepository) {
        this.returnRepository = returnRepository;
    }

    public ReturnRequest requestReturn(User user, OrderItem orderItem, String reason) {

        ReturnRequest request = new ReturnRequest();
        request.setOrderItem(orderItem);
        request.setReason(reason);
        request.setStatus(ReturnStatus.REQUESTED);

        return returnRepository.save(request);
    }
}
