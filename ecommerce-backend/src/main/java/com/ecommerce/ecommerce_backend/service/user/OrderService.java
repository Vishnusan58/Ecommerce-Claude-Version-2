package com.ecommerce.ecommerce_backend.service.user;

import com.ecommerce.ecommerce_backend.enums.OrderStatus;
import com.ecommerce.ecommerce_backend.model.*;
import com.ecommerce.ecommerce_backend.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final PremiumSubscriptionRepository premiumSubscriptionRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        CartItemRepository cartItemRepository,
                        CartRepository cartRepository, PremiumSubscriptionRepository premiumSubscriptionRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.premiumSubscriptionRepository = premiumSubscriptionRepository;
    }

    public Order placeOrder(User user, UserAddress address, LocalDate preferredDeliveryDate) {

        // 1️⃣ Fetch cart
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 2️⃣ Calculate total product amount
        double totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getProduct().getPrice() * item.getQuantity();
        }

        // 3️⃣ Check premium status
        boolean isPremium = premiumSubscriptionRepository
                .existsByUserAndActiveTrue(user);

        // 4️⃣ Create Order (THIS IS WHAT YOU ASKED ABOUT)
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order.setTotalAmount(totalAmount);

        // 5️⃣ Delivery charge logic
        double deliveryCharge = isPremium ? 0 : 50;
        order.setDeliveryCharge(deliveryCharge);

        // 6️⃣ Priority shipping
        order.setPriority(isPremium);

        // 7️⃣ Flexible delivery date (premium only)
        if (preferredDeliveryDate != null) {
            if (!isPremium) {
                throw new RuntimeException(
                        "Only premium users can choose delivery date"
                );
            }
            order.setPreferredDeliveryDate(preferredDeliveryDate);
        }

        // 8️⃣ Final amount
        order.setFinalAmount(totalAmount + deliveryCharge);

        // 9️⃣ Save order first
        Order savedOrder = orderRepository.save(order);

        // 🔟 Create order items
        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setSeller(cartItem.getProduct().getSeller());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setDiscountAtPurchase(0);

            orderItemRepository.save(orderItem);
        }

        // 1️⃣1️⃣ Clear cart after successful order
        cartItemRepository.deleteAll(cartItems);

        return savedOrder;
    }

    public List<Order> getOrders(User user) {
        return orderRepository.findByUser(user);
    }

    private boolean isPremiumUser(User user) {
        return premiumSubscriptionRepository
                .existsByUserAndActiveTrue(user);
    }

}
