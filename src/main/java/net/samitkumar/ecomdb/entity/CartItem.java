package net.samitkumar.ecomdb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("cart_items")
public record CartItem(@Id Long id, Long cartId, Long productId, Long quantity) {}
