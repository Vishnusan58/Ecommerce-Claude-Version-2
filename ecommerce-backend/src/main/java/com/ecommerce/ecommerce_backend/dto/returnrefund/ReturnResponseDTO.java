package com.ecommerce.ecommerce_backend.dto.returnrefund;

import java.time.LocalDateTime;

public class ReturnResponseDTO {

    private Long returnId;
    private String status;
    private LocalDateTime requestedAt;

    public ReturnResponseDTO() {}

    public Long getReturnId() {
        return returnId;
    }

    public void setReturnId(Long returnId) {
        this.returnId = returnId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }
}
