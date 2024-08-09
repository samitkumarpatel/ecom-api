package net.samitkumar.ecomdb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;

@Table("carts")
public record Cart(@Id Long id, Long userId, @Column("payload") Map<String,Object> payload) { }
