package net.samitkumar.ecomdb.entity;

import org.springframework.data.relational.core.mapping.Table;

@Table("orders")
public record Order(Long id, Long userId, Long totalAmount, String status) {
}
