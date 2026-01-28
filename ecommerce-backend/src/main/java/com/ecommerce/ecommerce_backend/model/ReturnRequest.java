package com.ecommerce.ecommerce_backend.model;

import com.ecommerce.ecommerce_backend.enums.ReturnStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "return_requests")
public class ReturnRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private OrderItem orderItem;

    @Enumerated(EnumType.STRING)
    private ReturnStatus status;

    private String reason;

    public ReturnRequest() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public OrderItem getOrderItem() { return orderItem; }
    public void setOrderItem(OrderItem orderItem) { this.orderItem = orderItem; }

    public ReturnStatus getStatus() { return status; }
    public void setStatus(ReturnStatus status) { this.status = status; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
