package net.samitkumar.ecomdb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("inventory")
public record Inventory(@Id Long id, Long productId, Integer quantity) {
}
