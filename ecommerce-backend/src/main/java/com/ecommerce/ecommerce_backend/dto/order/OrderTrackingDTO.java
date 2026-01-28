package com.ecommerce.ecommerce_backend.dto.order;

import java.util.List;

public class OrderTrackingDTO {

    private Long orderId;
    private String currentStatus;
    private List<OrderStatusTimelineDTO> timeline;

    public OrderTrackingDTO() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }

    public List<OrderStatusTimelineDTO> getTimeline() { return timeline; }
    public void setTimeline(List<OrderStatusTimelineDTO> timeline) { this.timeline = timeline; }
}
