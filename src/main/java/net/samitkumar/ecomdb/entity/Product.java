package net.samitkumar.ecomdb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("products")
public record Product(@Id Long id, String name, String description, Double price, Long quantity, Long categoryId) {
}
