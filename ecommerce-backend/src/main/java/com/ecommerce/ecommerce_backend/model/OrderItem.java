package com.ecommerce.ecommerce_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User seller;

    private int quantity;
    private double price;
    private double discountAtPurchase;

    public double getDiscountAtPurchase() {
        return discountAtPurchase;
    }

    public void setDiscountAtPurchase(double discountAtPurchase) {
        this.discountAtPurchase = discountAtPurchase;
    }

    public OrderItem() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
