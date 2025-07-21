package net.samitkumar.ecom.cart.entity;

import org.springframework.data.relational.core.mapping.Table;

@Table("cart_product_refs")
public record CartProductRef(Long cartId, Long productId, Integer quantity) {
}
