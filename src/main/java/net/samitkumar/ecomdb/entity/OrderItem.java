package net.samitkumar.ecomdb.entity;

import org.springframework.data.relational.core.mapping.Table;

@Table("order_items")
public record OrderItem(Long id, Long orderId, Long productId, Integer quantity, Double priceAtPurchase) {
}
