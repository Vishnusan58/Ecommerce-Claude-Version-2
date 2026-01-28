package com.ecommerce.ecommerce_backend.dto.recentlyviewed;

import java.util.List;

public class RecentlyViewedResponseDTO {

    private List<RecentlyViewedDTO> items;

    public RecentlyViewedResponseDTO() {}

    public List<RecentlyViewedDTO> getItems() { return items; }
    public void setItems(List<RecentlyViewedDTO> items) { this.items = items; }
}
