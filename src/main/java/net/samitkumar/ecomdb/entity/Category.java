package net.samitkumar.ecomdb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("categories")
public record Category(@Id Long id, String name, String description, @MappedCollection(idColumn = "category") Set<Product> products) {
}
