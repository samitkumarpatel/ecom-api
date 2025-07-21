package net.samitkumar.ecom.order.entity;

import org.springframework.data.relational.core.mapping.Table;

@Table("order_product_refs")
public record OrderProductRef(Long orderId, Long productId, Integer quantity) {
}
