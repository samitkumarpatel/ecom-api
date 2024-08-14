package net.samitkumar.ecomdb.entity.cart;

import org.springframework.data.relational.core.mapping.Table;

@Table("cart_product_refs")
public record CartProductRef(Long cartId, Long productId, Integer quantity) {
}
